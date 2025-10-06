

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

// =========================
// Product Entity
// =========================
class Product {
    private int id;
    private String name;
    private int quantity;
    private double price;

    public Product(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(String name, int quantity, double price) {
        this.id = -1; // temporary ID
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "{id=" + id + ", name='" + name + "', qty=" + quantity + ", price=" + price + "}";
    }
}

// =========================
// DAO Interface
// =========================
interface ProductDAOInterface {
    void addProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(int id);
    void updateProduct(Product product);
    void deleteProduct(int id);
}

// =========================
// In-Memory DAO Implementation
// =========================
class ProductDAOImpl implements ProductDAOInterface {
    private final List<Product> products = new ArrayList<>();
    private int idCounter = 1;

    @Override
    public void addProduct(Product product) {
        product.setId(idCounter++);
        products.add(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateProduct(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == product.getId()) {
                products.set(i, product);
                return;
            }
        }
    }

    @Override
    public void deleteProduct(int id) {
        products.removeIf(p -> p.getId() == id);
    }
}

// =========================
// JUnit Test Class
// =========================
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDAOTest {

    static ProductDAOInterface dao;
    static int productId; // ID of initial product

    @BeforeAll
    static void setup() {
        dao = new ProductDAOImpl();
        System.out.println("🧪 DAO Tests Started...");

        Product initial = new Product("Laptop", 5, 45000);
        dao.addProduct(initial);
        productId = initial.getId();
        System.out.println("Initial product added with ID: " + productId);
    }

    @Test
    @Order(1)
    void testAddProduct() {
        Product p = new Product("Phone", 10, 25000);
        dao.addProduct(p);

        Product saved = dao.getProductById(p.getId());
        assertNotNull(saved, "Added product should exist");
        assertEquals("Phone", saved.getName());
        System.out.println("✅ testAddProduct passed, ID: " + p.getId());
    }

    @Test
    @Order(2)
    void testGetAllProducts() {
        List<Product> list = dao.getAllProducts();
        assertNotNull(list, "getAllProducts should not return null");
        assertTrue(list.size() > 0, "There should be at least 1 product");
        System.out.println("✅ testGetAllProducts passed, total products: " + list.size());
    }

    @Test
    @Order(3)
    void testGetProductById() {
        Product p = dao.getProductById(productId);
        assertNotNull(p, "Product with stored ID should exist");
        assertEquals(productId, p.getId());
        System.out.println("✅ testGetProductById passed: " + p);
    }

    @Test
    @Order(4)
    void testUpdateProduct() {
        Product updated = new Product(productId, "Updated Laptop", 7, 50000);
        dao.updateProduct(updated);

        Product p = dao.getProductById(productId);
        assertNotNull(p, "Updated product should not be null");
        assertEquals("Updated Laptop", p.getName());
        assertEquals(7, p.getQuantity());
        assertEquals(50000, p.getPrice());
        System.out.println("✅ testUpdateProduct passed");
    }

    @Test
    @Order(5)
    void testDeleteProduct() {
        Product temp = new Product("Temp Product", 1, 100);
        dao.addProduct(temp);
        int tempId = temp.getId();

        int beforeSize = dao.getAllProducts().size();
        dao.deleteProduct(tempId);
        int afterSize = dao.getAllProducts().size();

        assertEquals(beforeSize - 1, afterSize, "Size should decrease by 1 after deletion");
        assertNull(dao.getProductById(tempId), "Deleted product should no longer exist");
        System.out.println("✅ testDeleteProduct passed");
    }

    @AfterAll
    static void cleanup() {
        System.out.println("🧹 All DAO tests finished!");
    }
}
