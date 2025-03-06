document.addEventListener('DOMContentLoaded', function() {
    // Xử lý chuyển đổi tab
    const tabButtons = document.querySelectorAll('.tab-button');
    const loginForm = document.querySelector('.login-form');
    const registerForm = document.querySelector('.register-form');

    tabButtons.forEach(button => {
        button.addEventListener('click', function() {
            tabButtons.forEach(btn => btn.classList.remove('active'));
            this.classList.add('active');
            
            if (this.textContent === 'ĐĂNG NHẬP') {
                loginForm.style.display = 'block';
                registerForm.style.display = 'none';
            } else {
                loginForm.style.display = 'none';
                registerForm.style.display = 'block';
            }
        });
    });

    // Xử lý hiển thị/ẩn mật khẩu cho tất cả các trường mật khẩu
    const passwordFields = document.querySelectorAll('.password-field');
    
    passwordFields.forEach(field => {
        const passwordInput = field.querySelector('input[type="password"]');
        const toggleButton = field.querySelector('.password-toggle');

        toggleButton.addEventListener('click', function() {
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
                toggleButton.textContent = '👁️‍🗨️';
            } else {
                passwordInput.type = 'password';
                toggleButton.textContent = '👁️';
            }
        });
    });

    // Xử lý nút đóng
    const closeButton = document.querySelector('.close-button');
    closeButton.addEventListener('click', function() {
        window.history.back();
    });
});
