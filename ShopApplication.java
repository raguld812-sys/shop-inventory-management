import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class representing a Product in the shop (Not public)
class Product {
    private String id;
    private String name;
    private double price;
    private int quantity;

    public Product(String id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return String.format("ID: %-8s | Name: %-15s | Price: $%-8.2f | Stock: %d", id, name, price, quantity);
    }
}

// Class to manage the inventory operations (Not public)
class InventoryManager {
    private List<Product> products;

    public InventoryManager() {
        products = new ArrayList<>();
        // Adding sample items for demonstration
        products.add(new Product("P001", "Laptop", 899.99, 15));
        products.add(new Product("P002", "Mouse", 25.50, 8));
        products.add(new Product("P003", "Keyboard", 45.00, 12));
        products.add(new Product("P004", "Monitor", 149.99, 5));
    }

    // 1. Add a new product
    public void addProduct(Product product) {
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(product.getId())) {
                System.out.println("❌ Error: Product with this ID already exists.");
                return;
            }
        }
        products.add(product);
        System.out.println("✅ Product added successfully!");
    }

    // 2. Update stock after a sale
    public void processSale(String id, int quantitySold) {
        Product product = findById(id);
        if (product == null) {
            System.out.println("❌ Error: Product not found.");
            return;
        }
        if (product.getQuantity() < quantitySold) {
            System.out.println("❌ Error: Insufficient stock. Only " + product.getQuantity() + " available.");
            return;
        }
        product.setQuantity(product.getQuantity() - quantitySold);
        System.out.println("✅ Sale successful! Updated stock for " + product.getName() + ": " + product.getQuantity());
    }

    // 3. Display products with stock below 10 (Low Stock Alert)
    public void displayLowStock() {
        System.out.println("\n--- ⚠️ Low Stock Alert (Below 10 Units) ---");
        boolean found = false;
        for (Product p : products) {
            if (p.getQuantity() < 10) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("All products have sufficient stock.");
        }
    }

    // 4. Search product by ID or Name
    public void searchProduct(String query) {
        System.out.println("\n--- 🔍 Search Results ---");
        boolean found = false;
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(query) || p.getName().equalsIgnoreCase(query)) {
                System.out.println(p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching product found for: " + query);
        }
    }

    // Helper method to look up a product by ID
    private Product findById(String id) {
        for (Product p : products) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    // Display all products in the shop
    public void displayAllProducts() {
        System.out.println("\n--- All Products ---");
        if (products.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            for (Product p : products) {
                System.out.println(p);
            }
        }
    }
}

// Main execution class (Must be PUBLIC and match the filename exactly)
public class ShopApplication {
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== SHOP INVENTORY MANAGEMENT =====");
            System.out.println("1. Add Product");
            System.out.println("2. Record a Sale (Update Stock)");
            System.out.println("3. View Low Stock Items (< 10)");
            System.out.println("4. Search Product (by ID or Name)");
            System.out.println("5. View All Products");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");
            
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number between 1 and 6: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Product ID: ");
                    String id = scanner.nextLine().trim();
                    System.out.print("Enter Product Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Initial Quantity: ");
                    int quantity = scanner.nextInt();
                    
                    manager.addProduct(new Product(id, name, price, quantity));
                    break;

                case 2:
                    System.out.print("Enter Product ID sold: ");
                    String saleId = scanner.nextLine().trim();
                    System.out.print("Enter Quantity sold: ");
                    int quantitySold = scanner.nextInt();
                    
                    manager.processSale(saleId, quantitySold);
                    break;

                case 3:
                    manager.displayLowStock();
                    break;

                case 4:
                    System.out.print("Enter Product ID or Name to search: ");
                    String searchQuery = scanner.nextLine().trim();
                    manager.searchProduct(searchQuery);
                    break;

                case 5:
                    manager.displayAllProducts();
                    break;

                case 6:
                    System.out.println("Thank you for using the Shop Management System!");
                    break;

                default:
                    System.out.println("Invalid option. Please choose between 1 and 6.");
            }
        } while (choice != 6);

        scanner.close();
    }
}
