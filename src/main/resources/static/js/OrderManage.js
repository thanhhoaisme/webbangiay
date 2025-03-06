document.addEventListener('DOMContentLoaded', function() {
    // Edit Order Form Handler
    const editOrderForm = document.getElementById('editOrderForm');
    if (editOrderForm) {
        editOrderForm.addEventListener('submit', function(e) {
            e.preventDefault();
            // Edit order logic here
            console.log('Form submitted');
            
            // Close modal
            const modal = bootstrap.Modal.getInstance(document.getElementById('editOrderModal'));
            if (modal) {
                modal.hide();
            }
        });
    }
    // // Edit Order Form Handler
    // const editOrderForm = document.getElementById('editOrderForm');
    // if (editOrderForm) {
    //     editOrderForm.addEventListener('submit', function(e) {
    //         e.preventDefault();
            
    //         // Capture form data
    //         const formData = new FormData(editOrderForm);
    //         const orderId = formData.get('orderId'); // Assuming there's a hidden input with name="orderId"
            
    //         // Send data to the server
    //         fetch(`/orders/${orderId}`, {
    //             method: 'PUT',
    //             body: formData
    //         })
    //         .then(response => response.json())
    //         .then(data => {
    //             if (data.success) {
    //                 // Update the order list on the page
    //                 const row = document.querySelector(`tr[data-order-id="${orderId}"]`);
    //                 row.querySelector('td:nth-child(2)').textContent = formData.get('customerName');
    //                 row.querySelector('td:nth-child(3)').textContent = formData.get('orderDate');
    //                 // Add other fields as necessary

    //                 // Close modal
    //                 const modal = bootstrap.Modal.getInstance(document.getElementById('editOrderModal'));
    //                 if (modal) {
    //                     modal.hide();
    //                 }
    //             } else {
    //                 console.error('Failed to update order:', data.message);
    //             }
    //         })
    //         .catch(error => {
    //             console.error('Error:', error);
    //         });
    //     });
    // }

    // Handle delete order button clicks
        const deleteButtons = document.querySelectorAll('.btn-danger');
        deleteButtons.forEach(button => {
            button.addEventListener('click', function(e) {
                e.preventDefault();
                
                // Get the order row
                const row = this.closest('tr');
                const orderId = row.querySelector('td:first-child').textContent;
                const customerName = row.querySelector('td:nth-child(2)').textContent;

                // Show confirmation dialog
                Swal.fire({
                    title: 'Are you sure?',
                    text: `Do you want to delete the order for ${customerName}?`,
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#d33',
                    cancelButtonColor: '#3085d6',
                    confirmButtonText: 'Yes, delete it!',
                    cancelButtonText: 'Cancel'
                }).then((result) => {
                    if (result.isConfirmed) {
                        // Here you would typically make an API call to delete the order
                        deleteOrder(orderId, row);
                    }
                });
            });
        });

    // Function to delete order (mock implementation)
    function deleteOrder(orderId, row) {
        console.log(`Order ${orderId} deleted`);
        row.remove(); // Remove row from table
    }
});