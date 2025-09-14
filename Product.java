
public class Product {
    private String productId;
    private String productName;
    private String description;
    private double price;
    private int quantity;

    // Constructor
    public Product(String productId, String productName, String description, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            System.out.println("Error: Price cannot be negative.");
            return;
        }
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            System.out.println("Error: Quantity cannot be negative.");
            return;
        }
        this.quantity = quantity;
    }

    // Method to update product quantity
    public void updateQuantity(int quantity) {
        if (this.quantity + quantity < 0) {
            System.out.println("Error: Insufficient quantity in stock.");
            return;
        }
        this.quantity += quantity;
    }

    // Method to check if product is in stock
    public boolean isInStock() {
        return quantity > 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public static void main(String[] args) {
        Product product = new Product("123", "Test Product", "This is a test product.", 10.99, 100);
        System.out.println(product.toString());
    }
}


