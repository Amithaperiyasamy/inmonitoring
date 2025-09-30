import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// =========================
// Custom Exceptions
// =========================
class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) { super(message); }
}

class InvalidDataException extends Exception {
    public InvalidDataException(String message) { super(message); }
}

// =========================
// InventoryItem Class
// =========================
class InventoryItem {
    private String productId;
    private String productName;
    private String description;
    private double price;
    private int quantity;

    public InventoryItem(String productId, String productName, String description, double price, int quantity) throws InvalidDataException {
        if (productId.isEmpty()) throw new InvalidDataException("❌ Product ID cannot be empty!");
        if (!productId.matches("[a-zA-Z0-9]+")) throw new InvalidDataException("❌ Product ID must be alphanumeric!");
        if (productName.isEmpty()) throw new InvalidDataException("❌ Product Name cannot be empty!");
        if (productName.matches("\\d+")) throw new InvalidDataException("❌ Product Name cannot be a number!");
        if (description.isEmpty()) throw new InvalidDataException("❌ Description cannot be empty!");
        if (description.matches("\\d+")) throw new InvalidDataException("❌ Description cannot be a number!");
        if (price < 0) throw new InvalidDataException("❌ Price cannot be negative!");
        if (quantity < 0) throw new InvalidDataException("❌ Quantity cannot be negative!");

        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public void updateQuantity(int qty) throws InvalidDataException {
        if (this.quantity + qty < 0) throw new InvalidDataException("⚠ Insufficient quantity in stock!");
        this.quantity += qty;
    }

    public boolean isInStock() { return quantity > 0; }

    // Display row with emojis
    public String toTableRow() {
        String statusEmoji = isInStock() ? "✅ In Stock" : "❌ Out of Stock";
        return String.format("🆔 %-6s | 🏷 %-15s | 📝 %-20s | 💲 $%-8.2f | 📦 %-5d | 📊 %-12s",
                productId, productName, description, price, quantity, statusEmoji);
    }

    // Getters for DB operations
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
}

// =========================
// InventoryManager Class
// =========================
class InventoryManager {
    private List<InventoryItem> items = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    // Database credentials
    private final String DB_URL = "jdbc:mysql://localhost:3306/inventorydb";
    private final String USER = "root";
    private final String PASS = "password";

    public InventoryManager() {
        loadItemsFromDB();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // DB CRUD
    private void addItemToDB(InventoryItem item) {
        String query = "INSERT INTO inventory (product_id, product_name, description, price, quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, item.getProductId());
            ps.setString(2, item.getProductName());
            ps.setString(3, item.getDescription());
            ps.setDouble(4, item.getPrice());
            ps.setInt(5, item.getQuantity());
            ps.executeUpdate();
        } catch (SQLException e) { System.out.println("❌ DB Error: " + e.getMessage()); }
    }

    private void updateItemInDB(InventoryItem item) {
        String query = "UPDATE inventory SET quantity=?, price=?, product_name=?, description=? WHERE product_id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, item.getQuantity());
            ps.setDouble(2, item.getPrice());
            ps.setString(3, item.getProductName());
            ps.setString(4, item.getDescription());
            ps.setString(5, item.getProductId());
            ps.executeUpdate();
        } catch (SQLException e) { System.out.println("❌ DB Error: " + e.getMessage()); }
    }

    private void deleteItemFromDB(String productId) {
        String query = "DELETE FROM inventory WHERE product_id=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) { System.out.println("❌ DB Error: " + e.getMessage()); }
    }

    private void loadItemsFromDB() {
        String query = "SELECT * FROM inventory";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            items.clear();
            while (rs.next()) {
                InventoryItem item = new InventoryItem(
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
                items.add(item);
            }
        } catch (Exception e) { System.out.println("❌ DB Load Error: " + e.getMessage()); }
    }

    // =========================
    // Utilities
    // =========================
    private String readNonEmptyString(String prompt, boolean allowNumbers) throws InvalidDataException {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) throw new InvalidDataException("❌ Input cannot be empty!");
        if (!allowNumbers && input.matches("\\d+")) throw new InvalidDataException("❌ Input cannot be numeric!");
        return input;
    }

    private double readDouble(String prompt) throws InvalidDataException {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            double val = Double.parseDouble(input);
            if (val < 0) throw new InvalidDataException("❌ Value cannot be negative!");
            return val;
        } catch (NumberFormatException e) { throw new InvalidDataException("❌ Please enter a valid number!"); }
    }

    private int readInt(String prompt) throws InvalidDataException {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        try {
            int val = Integer.parseInt(input);
            if (val < 0) throw new InvalidDataException("❌ Value cannot be negative!");
            return val;
        } catch (NumberFormatException e) { throw new InvalidDataException("❌ Please enter a valid integer!"); }
    }

    private InventoryItem findItemById(String id) throws ProductNotFoundException {
        return items.stream().filter(i -> i.getProductId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("❌ Item not found with ID " + id));
    }

    // =========================
    // Menu Options
    // =========================
    public void addItem() {
        try {
            String id = readNonEmptyString("🆔 Enter Product ID: ", true);
            String name = readNonEmptyString("🏷 Enter Product Name: ", false);
            String desc = readNonEmptyString("📝 Enter Description: ", false);
            double price = readDouble("💲 Enter Price: ");
            int qty = readInt("📦 Enter Quantity: ");

            InventoryItem item = new InventoryItem(id, name, desc, price, qty);
            items.add(item);
            addItemToDB(item);
            System.out.println("✅ Item added successfully! 🎉");
            displayItems();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage() + " ⚠ Please try again.");
        }
    }

    public void updateItem() {
        try {
            String id = readNonEmptyString("🆔 Enter Product ID to update: ", true);
            InventoryItem item = findItemById(id);

            System.out.print("📦 Enter quantity change (+ add / - remove): ");
            int qty = Integer.parseInt(scanner.nextLine().trim());
            item.updateQuantity(qty);

            updateItemInDB(item);
            System.out.println("✅ Quantity updated successfully!");
            displayItems();
        } catch (InvalidDataException | ProductNotFoundException | NumberFormatException e) {
            System.out.println(e.getMessage() + " ⚠ Please try again.");
        }
    }

    public void deleteItem() {
        try {
            String id = readNonEmptyString("🆔 Enter Product ID to delete: ", true);
            InventoryItem item = findItemById(id);
            items.remove(item);
            deleteItemFromDB(id);
            System.out.println("🗑 Item deleted successfully!");
            displayItems();
        } catch (InvalidDataException | ProductNotFoundException e) {
            System.out.println(e.getMessage() + " ⚠ Please try again.");
        }
    }

    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("❌ No items available! 😞");
            return;
        }

        System.out.println("\n=== 📊 Inventory Table ===");
        System.out.printf("🆔 %-6s | 🏷 %-15s | 📝 %-20s | 💲 %-9s | 📦 %-5s | 📊 %-12s%n",
                "ID", "Name", "Description", "Price", "Qty", "Status");
        System.out.println("-------------------------------------------------------------------------------");
        for (InventoryItem i : items) System.out.println(i.toTableRow());
    }

    public void run() {
        while (true) {
            System.out.println("\n=== 🛒 Inventory Manager Menu ===");
            System.out.println("⿡ Add Item");
            System.out.println("⿢ Update Item Quantity");
            System.out.println("⿣ Delete Item");
            System.out.println("⿤ Display Items");
            System.out.println("⿥ Exit");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();
            switch (option) {
                case "1" -> addItem();
                case "2" -> updateItem();
                case "3" -> deleteItem();
                case "4" -> displayItems();
                case "5" -> { System.out.println("👋 Goodbye!"); return; }
                default -> System.out.println("❌ Invalid option! Please enter 1-5. ⚠");
            }
        }
    }
}

// =========================
// Main Class
// =========================
public class ProductInventoryApp {
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
        manager.run();
    }
}
