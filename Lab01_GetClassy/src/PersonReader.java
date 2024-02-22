import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class PersonReader {
    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a Person file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();

            System.out.println("Selected file: " + selectedFilePath);

            try {
                List<Person> people = readPeopleFromFile(selectedFilePath);
                displayPeople(people);
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("File selection canceled.");
        }
    }

    private static List<Person> readPeopleFromFile(String filePath) throws IOException {
        List<Person> people = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0].trim();
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    String title = parts[3].trim();
                    int yearOfBirth = Integer.parseInt(parts[4].trim());

                    people.add(new Person(id, firstName, lastName, title, yearOfBirth));
                }
            }
        }

        return people;
    }

    private static void displayPeople(List<Person> people) {
        System.out.println("\nID\t\tFirstName\t\tLastName\t\tTitle\t\tYearOfBirth\t\tFullName\t\tFormalName\t\tAge");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");

        for (Person person : people) {
            String id = centerText(person.getId(), 3);
            String firstName = centerText(person.getFirstName(), 15);
            String lastName = centerText(person.getLastName(), 15);
            String title = centerText(person.getTitle(), 10);
            String yearOfBirth = centerText(Integer.toString(person.getYearOfBirth()), 12);
            String fullName = centerText(person.fullName(), 10);
            String formalName = centerText(person.formalName(), 10);
            String age = centerText(Integer.toString(person.getAge()), 3);

            String formattedLine = format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
                    id, firstName, lastName, title, yearOfBirth, fullName, formalName, age);
            System.out.println(formattedLine);
        }
    }

    private static String centerText(String text, int width) {
        return format("%-" + width + "s", format("%" + (text.length() + width) / 2 + "s", text));
    }
}
