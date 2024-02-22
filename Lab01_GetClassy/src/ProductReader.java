import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProductReader {
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a Product file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();

            System.out.println("Selected file: " + selectedFilePath);

            try {
                displayFileContent(selectedFilePath);
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("File selection canceled.");
        }
    }

    private static void displayFileContent(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            System.out.println("\nID\t\t\tName\t\t\tDescription\t\t\tCost");
            System.out.println("---------------------------------------------------------------");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = centerText(parts[0].trim(), 1);
                    String name = centerText(parts[1].trim(), 10);
                    String description = centerText(parts[2].trim(), 20);
                    String cost = centerText(parts[3].trim(), 15);

                    String formattedLine = String.format("%s\t%s\t%s\t%s", id, name, description, cost);
                    System.out.println(formattedLine);
                }
            }
        }
    }

    private static String centerText(String text, int width) {
        return String.format("%-" + width + "s", String.format("%" + (text.length() + width) / 2 + "s", text));
    }
}
