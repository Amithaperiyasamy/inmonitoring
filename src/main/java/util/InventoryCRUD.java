package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class InventoryCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "students";
    private static final String USER = "root";
    private static final String PASSWORD = "12345"; // replace with your actual password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
    }

    public static void createTable() throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS inventory (id INT AUTO_INCREMENT PRIMARY KEY, item_name VARCHAR(255) NOT NULL, quantity INT NOT NULL, location VARCHAR(255) NOT NULL)";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
            System.out.println("Connected to database: " + DB_NAME);
        }
    }

    public static void addItem(String itemName, int quantity, String location) throws SQLException {
        String query = "INSERT INTO inventory (item_name, quantity, location) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemName);
            stmt.setInt(2, quantity);
            stmt.setString(3, location);
            stmt.executeUpdate();
        }
    }

    public static void updateItem(int id, String itemName, int quantity, String location) throws SQLException {
        String query = "UPDATE inventory SET item_name = ?, quantity = ?, location = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, itemName);
            stmt.setInt(2, quantity);
            stmt.setString(3, location);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    public static void deleteItem(int id) throws SQLException {
        String query = "DELETE FROM inventory WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public static void printInventory() throws SQLException {
        String query = "SELECT * FROM inventory";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Item Name: " + rs.getString("item_name") + ", Quantity: " + rs.getInt("quantity") + ", Location: " + rs.getString("location"));
            }
        }
    }

    public static void main(String[] args) {
        try {
            createTable();
            addItem("Item 1", 10, "Location 1");
            addItem("Item 2", 20, "Location 2");
            System.out.println("Inventory:");
            printInventory();
            updateItem(1, "Item 1 Updated", 15, "Location 1 Updated");
            System.out.println("Updated Inventory:");
            printInventory();
            deleteItem(2);
            System.out.println("Inventory after deletion:");
            printInventory();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


