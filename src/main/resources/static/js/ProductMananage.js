document.addEventListener('DOMContentLoaded', function() {
   /// Handle delete order
    const deleteButtons = document.querySelectorAll('.btn-danger');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Get the order row
            const row = this.closest('tr');
            const productId = row.querySelector('td:first-child').textContent;
            const productName = row.querySelector('td:nth-child(2)').textContent;

            // Show confirmation dialog
            Swal.fire({
                title: 'Are you sure?',
                text: `Do you want to delete the order for ${productName}?`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Yes, delete it!',
                cancelButtonText: 'Cancel'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Here you would typically make an API call to delete the order
                    deleteOrder(productId, row);
                }
            });
        });
    });

    // Function to delete order (mock implementation)
    function deleteOrder(productId, row) {
        console.log(`Order ${productId} deleted`);
        row.remove(); // Remove row from table
    }
    
// // Function to handle the actual deletion
    // function deleteProduct(productId, row) {
    //     // You would typically make an API call here
    //     // For now, we'll simulate an API call with a timeout
        
    //     // Show loading state
    //     Swal.fire({
    //         title: 'Deleting...',
    //         text: 'Please wait',
    //         allowOutsideClick: false,
    //         didOpen: () => {
    //             Swal.showLoading();
    //         }
    //     });

    //     // Simulate API call
    //     setTimeout(() => {
    //         try {
    //             // Remove the row from the table
    //             row.remove();
                
    //             // Show success message
    //             Swal.fire({
    //                 title: 'Deleted!',
    //                 text: 'The product has been deleted successfully.',
    //                 icon: 'success',
    //                 timer: 2000,
    //                 showConfirmButton: false
    //             });
                
    //             // In real implementation, you would make an API call like this:
    //             /*
    //             fetch(`/api/products/${productId}`, {
    //                 method: 'DELETE',
    //                 headers: {
    //                     'Content-Type': 'application/json',
    //                     // Add any authentication headers here
    //                 }
    //             })
    //             .then(response => {
    //                 if (response.ok) {
    //                     row.remove();
    //                     Swal.fire('Deleted!', 'Product has been deleted.', 'success');
    //                 } else {
    //                     throw new Error('Failed to delete product');
    //                 }
    //             })
    //             .catch(error => {
    //                 Swal.fire('Error!', error.message, 'error');
    //             });
    //             */
                
    //         } catch (error) {
    //             Swal.fire({
    //                 title: 'Error!',
    //                 text: 'Failed to delete the product.',
    //                 icon: 'error'
    //             });
    //         }
    //     }, 1000);
    // }


    
});