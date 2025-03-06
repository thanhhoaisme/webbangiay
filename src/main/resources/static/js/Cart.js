document.addEventListener('DOMContentLoaded', function() {
    const decreaseButtons = document.querySelectorAll('.decrease-quantity');
    const increaseButtons = document.querySelectorAll('.increase-quantity');
    const quantityElements = document.querySelectorAll('.quantity');
    const removeButtons = document.querySelectorAll('.remove-item');
    const checkoutButton = document.querySelector('.checkout-button');
    const checkoutMessage = document.getElementById('checkout-message');

    decreaseButtons.forEach((button, index) => {
        button.addEventListener('click', function() {
            let quantity = parseInt(quantityElements[index].textContent);
            if (quantity > 1) {
                quantity--;
                quantityElements[index].textContent = quantity;
            }
        });
    });

    increaseButtons.forEach((button, index) => {
        button.addEventListener('click', function() {
            let quantity = parseInt(quantityElements[index].textContent);
            quantity++;
            quantityElements[index].textContent = quantity;
        });
    });

    removeButtons.forEach((button, index) => {
        button.addEventListener('click', function() {
            const cartItem = button.closest('.cart-item');
            if (cartItem) {
                cartItem.remove();
            }
        });
    });

    checkoutButton.addEventListener('click', function() {
        checkoutMessage.style.display = 'block';

        setTimeout(() => {
            checkoutMessage.style.display = 'none';
        }, 1500);
    });
});
