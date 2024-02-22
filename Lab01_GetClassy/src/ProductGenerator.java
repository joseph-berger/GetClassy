import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductGenerator {
    public static void main(String[] args) {
        ArrayList<Product> productsData = new ArrayList<>();

        System.out.println("Enter data for each product. Type 'exit' to finish.");

        while (true) {
            Product product = getProductData(new Scanner(System.in));

            if (product == null) {
                break;
            }

            productsData.add(product);
        }

        System.out.println("Enter a file name to save the product data:");
        String fileName = SafeInput.getNonZeroLenString(new Scanner(System.in), "Please enter a valid file name.");

        saveToFile(fileName, productsData);

        System.out.println("Product data saved to file: " + fileName);
    }

    private static Product getProductData(Scanner scanner) {
        System.out.print("Enter product data (ID, Name, Description, Cost): ");
        String line = SafeInput.getNonZeroLenString(scanner, "Data Recorded");
        if (line.equalsIgnoreCase("exit")) {
            return null;
        }

        String[] parts = line.split(",");
        if (parts.length == 4) {
            String id = parts[0].trim();
            String name = parts[1].trim();
            String description = parts[2].trim();
            double cost = Double.parseDouble(parts[3].trim());

            return new Product(id, name, description, cost);
        } else {
            System.out.println("Invalid data format. Please enter data in the correct format.");
            return getProductData(scanner);
        }
    }

    private static void saveToFile(String fileName, ArrayList<Product> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Product product : data) {
                writer.write(product.toCSVDataRecord());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error while saving to file: " + e.getMessage());
        }
    }
}
