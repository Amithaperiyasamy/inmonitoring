package model;

import java.sql.*;
public class ProductDatabase {
    public static void main(String[] args) {
        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/inventory";
        String username = "root";
        String password = "12345";

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            Connection conn = DriverManager.getConnection(url, username, password);

            // Create a statement
            Statement stmt = conn.createStatement();

            // Create the product table
            String createTableQuery = "CREATE TABLE IF NOT EXISTS products (id INT AUTO_INCREMENT, name VARCHAR(255), price DECIMAL(10, 2), PRIMARY KEY (id))";
            stmt.executeUpdate(createTableQuery);

            // Insert some data into the table
            String insertQuery = "INSERT INTO products (name, price) VALUES ('Modem', 1950.99), ('Headset', 900.99), ('Laptop', 590000.99)";
            stmt.executeUpdate(insertQuery);

            // Retrieve the data from the table
            String selectQuery = "SELECT * FROM products";
            ResultSet rs = stmt.executeQuery(selectQuery);

            // Print the data in a table format
            System.out.println("+----+----------+--------+");
            System.out.println("| ID | Name     | Price  |");
            System.out.println("+----+----------+--------+");
            while (rs.next()) {
                System.out.printf("| %-2d | %-8s | %-6.2f |\n", rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
                System.out.println("+----+----------+--------+");
            }

            // Close the resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
