class CategoryPage {
    constructor() {
        this.productGrid = document.getElementById('productGrid');
        this.categoryTitle = document.getElementById('categoryTitle');
        this.sortButtons = document.querySelectorAll('.sort-options button');
        this.filterInputs = document.querySelectorAll('.filters input');
        
        this.category = this.getCategoryFromUrl();
        this.products = [];
        
        this.init();
    }

    getCategoryFromUrl() {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get('type') || 'all';
    }

    async init() {
        // Khởi tạo trang
        this.updateCategoryTitle();
        await this.loadProducts();
        this.setupEventListeners();
    }

    updateCategoryTitle() {
        this.categoryTitle.textContent = this.category.toUpperCase();
    }

    async loadProducts() {
        try {
            // Trong thực tế, đây sẽ là API call
            const response = await fetch(`/api/products?category=${this.category}`);
            this.products = await response.json();
            this.renderProducts();
        } catch (error) {
            console.error('Error loading products:', error);
            this.showError('Không thể tải sản phẩm. Vui lòng thử lại sau.');
        }
    }

    renderProducts() {
        if (!this.products.length) {
            this.productGrid.innerHTML = '<p>Không tìm thấy sản phẩm nào.</p>';
            return;
        }

        this.productGrid.innerHTML = this.products.map(product => `
            <div class="product-card">
                <div class="product-image">
                    <img src="${product.image}" alt="${product.name}">
                </div>
                <p class="product-title">${product.name}</p>
                <p class="product-price">$${product.price}</p>
            </div>
        `).join('');
    }

    setupEventListeners() {
        // Xử lý sắp xếp
        this.sortButtons.forEach(button => {
            button.addEventListener('click', () => this.handleSort(button));
        });

        // Xử lý lọc
        this.filterInputs.forEach(input => {
            input.addEventListener('change', () => this.handleFilter());
        });
    }

    handleSort(clickedButton) {
        // Cập nhật trạng thái active
        this.sortButtons.forEach(btn => btn.classList.remove('active'));
        clickedButton.classList.add('active');

        // Sắp xếp sản phẩm
        const sortType = clickedButton.textContent.toLowerCase();
        switch(sortType) {
            case 'price ascending':
                this.products.sort((a, b) => a.price - b.price);
                break;
            case 'price descending':
                this.products.sort((a, b) => b.price - a.price);
                break;
            case 'rating':
                this.products.sort((a, b) => b.rating - a.rating);
                break;
            default: // 'new'
                this.products.sort((a, b) => new Date(b.date) - new Date(a.date));
        }
        this.renderProducts();
    }

    handleFilter() {
        // Lấy tất cả các filter đã chọn
        const selectedSizes = Array.from(document.querySelectorAll('.filters input[type="checkbox"]:checked'))
            .map(input => input.value);
        
        const priceRange = document.querySelector('.filters input[type="range"]').value;

        // Lọc sản phẩm
        const filteredProducts = this.products.filter(product => {
            const sizeMatch = selectedSizes.length === 0 || selectedSizes.includes(product.size);
            const priceMatch = product.price <= priceRange;
            return sizeMatch && priceMatch;
        });

        this.renderFilteredProducts(filteredProducts);
    }

    renderFilteredProducts(filteredProducts) {
        this.products = filteredProducts;
        this.renderProducts();
    }

    showError(message) {
        this.productGrid.innerHTML = `<p class="error-message">${message}</p>`;
    }
}

// Khởi tạo trang khi document đã load
document.addEventListener('DOMContentLoaded', () => {
    new CategoryPage();
});
