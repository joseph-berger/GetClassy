import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PersonGenerator {
    public static void main(String[] args) {
        ArrayList<Person> personsData = new ArrayList<>();

        System.out.println("Enter data for each person. Type 'exit' to finish.");

        while (true) {
            Person person = getPersonData(new Scanner(System.in));

            if (person == null) {
                break;
            }

            personsData.add(person);
        }

        System.out.println("Enter a file name to save the data:");
        String fileName = SafeInput.getNonZeroLenString(new Scanner(System.in), "Please enter a valid file name.");

        saveToFile(fileName, personsData);

        System.out.println("Data saved to file: " + fileName);
    }

    private static Person getPersonData(Scanner scanner) {
        System.out.print("Enter person data (ID, FirstName, LastName, Title, YearOfBirth): ");
        String line = SafeInput.getNonZeroLenString(scanner, "Data Recorded");
        if (line.equalsIgnoreCase("exit")) {
            return null;
        }

        String[] parts = line.split(",");
        if (parts.length == 5) {
            String id = parts[0].trim();
            String firstName = parts[1].trim();
            String lastName = parts[2].trim();
            String title = parts[3].trim();
            int yearOfBirth = Integer.parseInt(parts[4].trim());

            return new Person(id, firstName, lastName, title, yearOfBirth);
        } else {
            System.out.println("Invalid data format. Please enter data in the correct format.");
            return getPersonData(scanner);
        }
    }

    private static void saveToFile(String fileName, ArrayList<Person> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Person person : data) {
                writer.write(person.toCSVDataRecord());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error while saving to file: " + e.getMessage());
        }
    }
}
