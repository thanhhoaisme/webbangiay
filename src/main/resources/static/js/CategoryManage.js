document.addEventListener('DOMContentLoaded', function() {
    //Handle delete category
    const deleteButtons = document.querySelectorAll('.btn-danger');
    deleteButtons.forEach(button => {  
        button.addEventListener('click',function(e) {
            e.preventDefault();

            // Get the category row
            const row = this.closest('tr');
            const categoryId = row.querySelector('td:first-child').textContent;
            const categoryName = row.querySelector('td:nth-child(2)').textContent;

            // Show confirmation dialog
            Swal.fire({
                title:'Are you sure?',
                text:`Do you want to delete the category for ${categoryName}?`,
                icon:'warning',
                showCancelButton:true,
                confirmButtonColor:'#d33',
                cancelButtonColor:'#3085d6',
                confirmButtonText:'Yes, delete it!',
                cancelButtonText:'Cancel'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Here you would typically make an API call to delete the category
                    deleteCategory(categoryId,row);
                }
            });
        });
    });

    // Function to delete category 
    function deleteCategory(categoryId,row) {
        console.log(`Category ${categoryId} deleted`);
        row.remove(); // Remove row from table
    }

    //Edit Category Form Handler
    const editCategoryForm = document.getElementById('editCategoryForm');
    if (editCategoryForm) {
        editCategoryForm.addEventListener('submit', function(e) {
            e.preventDefault();
            // Edit order logic here
            console.log('Form submitted');
            
            // Close modal
            const modal = bootstrap.Modal.getInstance(document.getElementById('editCategoryModal'));
            if (modal) {
                modal.hide();
            }
        });
    }
    //function to edit category
    function editCategory(categoryId,row){
        console.log(`Category ${categoryId} edited`);
        row.remove(); // Remove row from table
    }
});