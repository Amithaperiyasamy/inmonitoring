
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ProductManagementSystem {
    private JFrame frame;
    private JTextField nameField;
    private JTextField priceField;
    private DefaultTableModel tableModel;
    private JTable table;

    public ProductManagementSystem() {
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Product Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create panel for adding products
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new GridLayout(3, 2));
        addPanel.add(new JLabel("Product Name:"));
        nameField = new JTextField();
        addPanel.add(nameField);
        addPanel.add(new JLabel("Product Price:"));
        priceField = new JTextField();
        addPanel.add(priceField);
        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(new AddProductListener());
        addPanel.add(new JLabel()); // Empty label for layout
        addPanel.add(addButton);

        // Create table for displaying products
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Price");
        table = new JTable(tableModel);

        // Create scroll pane for table
        JScrollPane scrollPane = new JScrollPane(table);

        // Add components to frame
        frame.add(addPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Load existing products from file
        loadProducts();

        frame.pack();
        frame.setVisible(true);
    }

    private void loadProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("products.csv"))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                tableModel.addRow(parts);
            }
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    private class AddProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String price = priceField.getText();

            try (PrintWriter writer = new PrintWriter(new FileWriter("products.csv", true))) {
                if (tableModel.getRowCount() == 0) {
                    writer.println("Name,Price");
                }
                writer.println(name + "," + price);
                tableModel.addRow(new Object[] {name, price});
                nameField.setText("");
                priceField.setText("");
                JOptionPane.showMessageDialog(frame, "Product added successfully!");
            } catch (IOException ex) {
                System.out.println("Error adding product: " + ex.getMessage());
                JOptionPane.showMessageDialog(frame, "Error adding product!");
            }
        }
    }

    public static void main(String[] args) {
        new ProductManagementSystem();
    }
}


