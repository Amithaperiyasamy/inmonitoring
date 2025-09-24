import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// =========================
// Product entity class
// =========================
class Product {
    int id;
    String name;
    int quantity;
    double price;

    public Product(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", name='" + name + "', qty=" + quantity + ", price=" + price + "}";
    }
}

// =========================
// DAO Interface
// =========================
interface ProductDAOInterface {
    void addProduct(Product product) throws SQLException;
    List<Product> getAllProducts() throws SQLException;
    Product getProductById(int id) throws SQLException;
    void updateProduct(Product product) throws SQLException;
    void deleteProduct(int id) throws SQLException;
}

// =========================
// DAO Implementation
// =========================
class ProductDAOImpl implements ProductDAOInterface {
    private Connection connection;

    public ProductDAOImpl() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // ✅ Change username/password for your system
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/inventorydb",
                    "root",
                    "12345"
            );
            System.out.println("✅ Database connection successful!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Error connecting to database: " + e.getMessage());
        }
    }

    @Override
    public void addProduct(Product product) throws SQLException {
        if (connection == null) throw new SQLException("⚠️ No DB connection.");
        String sql = "INSERT INTO products (name, quantity, price) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.name);
            ps.setInt(2, product.quantity);
            ps.setDouble(3, product.price);
            ps.executeUpdate();
            System.out.println("✅ Added: " + product);
        }
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        if (connection == null) throw new SQLException("⚠️ No DB connection.");
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }
        }
        return products;
    }

    @Override
    public Product getProductById(int id) throws SQLException {
        if (connection == null) throw new SQLException("⚠️ No DB connection.");
        String sql = "SELECT * FROM products WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
            } else {
                throw new SQLException("❌ No product found with ID " + id);
            }
        }
    }

    @Override
    public void updateProduct(Product product) throws SQLException {
        if (connection == null) throw new SQLException("⚠️ No DB connection.");
        String sql = "UPDATE products SET name=?, quantity=?, price=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.name);
            ps.setInt(2, product.quantity);
            ps.setDouble(3, product.price);
            ps.setInt(4, product.id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Updated: " + product);
            } else {
                throw new SQLException("❌ No product found with ID " + product.id);
            }
        }
    }

    @Override
    public void deleteProduct(int id) throws SQLException {
        if (connection == null) throw new SQLException("⚠️ No DB connection.");
        String sql = "DELETE FROM products WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Deleted product with ID " + id);
            } else {
                throw new SQLException("❌ No product found with ID " + id);
            }
        }
    }
}

// =========================
// Main Class
// =========================
public class ProductDAO {
    public static void main(String[] args) {
        ProductDAOInterface dao = new ProductDAOImpl();

        try {
            // 1. Add products
            dao.addProduct(new Product("Laptop", 10, 75000.0));
            dao.addProduct(new Product("Phone", 20, 30000.0));

            // 2. Show all in single line
            System.out.println("📦 All Products: " + dao.getAllProducts());

            // 3. Get by ID
            System.out.println("🔍 Product ID 1: " + dao.getProductById(1));

            // 4. Update
            dao.updateProduct(new Product(1, "Gaming Laptop", 12, 85000.0));

            // 5. Delete
            dao.deleteProduct(2);

            // 6. Final list
            System.out.println("📦 Final Products: " + dao.getAllProducts());

        } catch (SQLException e) {
            System.out.println("⚠️ " + e.getMessage());
        }
    }
}
