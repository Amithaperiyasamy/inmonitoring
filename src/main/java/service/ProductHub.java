package service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class ProductHub {
    static class InvalidInputException extends Exception {
        public InvalidInputException(String message) {
            super(message);
        }
    }

    static class DuplicateProductException extends Exception {
        public DuplicateProductException(String message) {
            super(message);
        }
    }

    static class ProductNotAddedException extends Exception {
        public ProductNotAddedException(String message) {
            super(message);
        }
    }

    // ProductManager class
    static class ProductManager {
        private Map<String, String> products = new HashMap<>();
        private Scanner scanner = new Scanner(System.in);

        public void addProduct() {
            try {
                System.out.print("Enter product ID: ");
                String id = scanner.nextLine();
                System.out.print("Enter product name: ");
                String name = scanner.nextLine();

                if (id.isEmpty() || name.isEmpty()) {
                    throw new InvalidInputException("Product ID and name cannot be empty.");
                }

                if (products.containsKey(id)) {
                    throw new DuplicateProductException("Product with ID " + id + " already exists.");
                }

                products.put(id, name);
                System.out.println("Product added successfully.");
            } catch (InvalidInputException e) {
                System.out.println("Invalid input: " + e.getMessage());
            } catch (DuplicateProductException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please enter a different product ID.");
            }
        }

        public void displayProducts() {
            if (products.isEmpty()) {
                System.out.println("No products available.");
            } else {
                products.forEach((id, name) -> System.out.println("ID: " + id + ", Name: " + name));
            }
        }
    }

    public static void main(String[] args) {
        ProductManager manager = new ProductManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. Display Products");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    manager.addProduct();
                    break;
                case 2:
                    manager.displayProducts();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }
}


