import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InventoryCRUD {
    private static final String URL = "jdbc:mysql:";
    private static final String USER = "//localhost:3306/InventoryDB";
    private static final String User = "root";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addProduct(String productName, int quantity, double price) {
        String query = "INSERT INTO Inventory (product_name, quantity, price) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, productName);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, price);
            stmt.executeUpdate();
            System.out.println("Product added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewProducts() {
        String query = "SELECT * FROM Inventory";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("product_name") + ", Quantity: " + rs.getInt("quantity") + ", Price: " + rs.getDouble("price"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(int id, int newQuantity) {
        String query = "UPDATE Inventory SET quantity = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Product updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        String query = "DELETE FROM Inventory WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Product deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        InventoryCRUD inventory = new InventoryCRUD();
        inventory.addProduct("Laptop", 10, 75000.00);
        System.out.println("All products:");
        inventory.viewProducts();
        inventory.updateProduct(1, 15);
        System.out.println("Updated product:");
        inventory.viewProducts();
        inventory.deleteProduct(1);
        System.out.println("Products after deletion:");
        inventory.viewProducts();
    }
}



