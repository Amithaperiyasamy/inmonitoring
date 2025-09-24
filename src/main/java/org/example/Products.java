
    class Products {
    private String productId;
    private String productName;
    private String description;
    private double price;
    private int quantity;

    public Products(String productId, String productName, String description, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        if (price < 0) {
            System.out.println("Error: Price cannot be negative.");
            return;
        }
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            System.out.println("Error: Quantity cannot be negative.");
            return;
        }
        this.quantity = quantity;
    }

    public void updateQuantity(int quantity) {
        if (this.quantity + quantity < 0) {
            System.out.println("Error: Insufficient quantity in stock.");
            return;
        }
        this.quantity += quantity;
    }

    public boolean isInStock() {
        return quantity > 0;
    }

    public void displayProduct() {
        System.out.println("Product ID: " + productId);
        System.out.println("Product Name: " + productName);
        System.out.println("Description: " + description);
        System.out.println("Price: " + price);
        System.out.println("Quantity: " + quantity);
    }

    public static void main(String[] args) {
        Products products = new Products("123", "Test Product", "This is a test product.", 10.99, 100);
        products.displayProduct(); // Corrected method name
    }
}



