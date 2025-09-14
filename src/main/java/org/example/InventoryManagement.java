import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class InventoryManagement {
    private Map<String, Integer> products;
    private Scanner scanner;

    public InventoryManagement() {
        this.products = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

    public void addProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (products.containsKey(name)) {
            System.out.println("Product already exists. Please update the quantity instead.");
        } else {
            products.put(name, quantity);
            System.out.println("Product added successfully!");
        }
    }

    public void removeProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        if (products.containsKey(name)) {
            products.remove(name);
            System.out.println("Product removed successfully!");
        } else {
            System.out.println("Product not found.");
        }
    }

    public void updateProductQuantity() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (products.containsKey(name)) {
            products.put(name, quantity);
            System.out.println("Product quantity updated successfully!");
        } else {
            System.out.println("Product not found.");
        }
    }

    public void searchProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        if (products.containsKey(name)) {
            System.out.println("Product found with quantity: " + products.get(name));
        } else {
            System.out.println("Product not found.");
        }
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\nInventory Management System");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Update Product Quantity");
            System.out.println("4. Search Product");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    removeProduct();
                    break;
                case 3:
                    updateProductQuantity();
                    break;
                case 4:
                    searchProduct();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }

    public static void main(String[] args) {
        InventoryManagement inventory = new InventoryManagement();
        inventory.displayMenu();
}
}