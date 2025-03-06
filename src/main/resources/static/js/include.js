// Sử dụng fetch để lấy file header.html
fetch('header.html')
    .then(response => response.text())
    .then(data => {
        // Chèn nội dung vào phần tử có id là header
        document.getElementById('header-placeholder').innerHTML = data;
    })
    .catch(error => console.error('Error:', error));
// Sử dụng fetch để lấy file footer.html
fetch('footer.html')
    .then(response => response.text())
    .then(data => {
        // Chèn nội dung vào phần tử có id là footer
        document.getElementById('footer-placeholder').innerHTML = data;
    })
    .catch(error => console.error('Error:', error));
// Sử dụng fetch để lấy file sidebar.html
fetch('sidebar.html')
    .then(response => response.text())
    .then(data => {
        // Chèn nội dung vào phần tử có id là sidebar
        document.getElementById('sidebar-placeholder').innerHTML = data;
    })
    .catch(error => console.error('Error:', error));
fetch('navbar.html')
    .then(response => response.text())
    .then(data => {
        document.getElementById('navbar-placeholder').innerHTML = data;
    })
    .catch(error => console.error('Error:',error));