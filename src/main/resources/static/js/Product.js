document.addEventListener('DOMContentLoaded', function() {
    // Size button selection
    document.querySelectorAll('.size-btn').forEach(button => {
        button.addEventListener('click', function() {
            // Remove selected class from all buttons
            document.querySelectorAll('.size-btn').forEach(btn => {
                btn.classList.remove('selected');
            });
            // Add selected class to clicked button
            this.classList.add('selected');
        });
    });
});
