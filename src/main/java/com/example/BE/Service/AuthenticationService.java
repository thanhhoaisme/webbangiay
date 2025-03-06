package com.example.BE.Service;

import com.example.BE.DTO.request.AuthenticationRequest;
import com.example.BE.DTO.request.IntrospectRequest;
import com.example.BE.DTO.request.LogoutRequest;
import com.example.BE.DTO.request.RefreshRequest;
import com.example.BE.DTO.response.AuthenticationResponse;
import com.example.BE.DTO.response.IntrospectResponse;
import com.example.BE.Entity.InvalidatedToken;
import com.example.BE.Entity.User;
import com.example.BE.Exception.AppException;
import com.example.BE.Exception.ErrorCode;
import com.example.BE.Repository.InvalidatedTokenRepository;
import com.example.BE.Repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationService {
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    @NonFinal
    @Value("${jwt.tokenValidityInSeconds}")
    protected long TOKEN_VALIDITY_IN_SECONDS;
    @NonFinal
    @Value("${jwt.refreshTokenValidityInSeconds}")
    protected long REFRESH_TOKEN_VALIDITY_IN_SECONDS;

    public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
        var token = request.getToken(); // Lấy token từ request
        boolean isValid = true;

        try {
            verifyToken(token,false); // Xác minh token
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Tìm user theo username
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Khởi tạo BCrypt encoder
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        // So sánh mật khẩu
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        // Tạo JWT token
        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);

            String jwtid = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jwtid)
                    .expiryTime(expiryTime)
                    .build();


            invalidatedTokenRepository.save(invalidatedToken);
        }catch (AppException e){
            log.info("Token already expired or invalid");
        }
    }
    private SignedJWT verifyToken(String token , boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes()); // Tạo verifier với SIGNER_KEY

        SignedJWT signedJWT = SignedJWT.parse(token); // Parse token thành đối tượng SignedJWT

        signedJWT.verify(verifier); // Xác minh chữ ký

        Date expirationTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                    .toInstant().plus(REFRESH_TOKEN_VALIDITY_IN_SECONDS, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime(); // Lấy thời gian hết hạn

        var verified = signedJWT.verify(verifier); // Kiểm tra lại chữ ký

        if(!verified || expirationTime.before(new Date())) // Nếu chữ ký không hợp lệ hoặc token đã hết hạn
            throw new AppException(ErrorCode.UNAUTHENTICATED); // Ném lỗi UNAUTHENTICATED

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    public AuthenticationResponse refeshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signJwt = verifyToken(request.getToken(),true);
        var jit = signJwt.getJWTClaimsSet().getJWTID();
        var expiryTime = signJwt.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signJwt.getJWTClaimsSet().getSubject();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }
    private String generateToken(User user) {
        // Tạo header với thuật toán HS512
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Tạo các claims (thông tin trong token)
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername()) // Subject là username
                .issuer("demo.com") // Issuer (người phát hành token)
                .issueTime(new Date()) // Thời điểm phát hành
                .expirationTime(new Date(Instant.now().plus(TOKEN_VALIDITY_IN_SECONDS, ChronoUnit.SECONDS).toEpochMilli())) // Hết hạn sau 1 giờ
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user)) // Thêm scope (ví dụ: Role)
                .build();

        // Tạo payload từ claims
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // Tạo JWS object và ký token
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes())); // Ký token bằng SIGNER_KEY
            return jwsObject.serialize(); // Trả về token dạng chuỗi
        } catch (JOSEException e) {
            log.error("Error signing token", e);
            throw new RuntimeException(e); // Xử lý lỗi ký token
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" "); // Tạo StringJoiner với delimiter "  "
        //Delimiter: Ký tự hoặc chuỗi được sử dụng để phân tách giữa các phần tử.
        //Prefix và Suffix: Chuỗi được thêm vào đầu và cuối kết quả cuối cùng.
        // Logic thêm scope (ví dụ: Role)
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString(); // Trả về chuỗi rỗng (chưa triển khai)
    }
}
