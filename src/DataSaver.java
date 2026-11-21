import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DataSaver {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();

        boolean moreRecords;
        do {
            String firstName = SafeInput.getNonZeroLenString(in, "Enter First Name").trim();
            String lastName = SafeInput.getNonZeroLenString(in, "Enter Last Name").trim();
            String idNumber = SafeInput.getRegExString(in, "Enter 6-digit ID Number (e.g., 000001)", "\\d{6}");
            String email = SafeInput.getNonZeroLenString(in, "Enter Email").trim();
            int yearOfBirth = SafeInput.getRangedInt(in, "Enter Year of Birth", 1000, 9999);

            String csvLine = firstName + ", " + lastName + ", " + idNumber + ", " + email + ", " + yearOfBirth;
            records.add(csvLine);

            moreRecords = SafeInput.getYNConfirm(in, "Do you want to enter another record?");
        } while (moreRecords);

        String fileName = SafeInput.getNonZeroLenString(in, "Enter output file name (add .csv)").trim();
        if (!fileName.toLowerCase().endsWith(".csv")) {
            fileName += ".csv";
        }

        Path filePath = Paths.get("src", fileName);

        try {
            Files.createDirectories(filePath.getParent());
            try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(filePath));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {

                for (String rec : records) {
                    writer.write(rec);
                    writer.newLine();
                }
            }
            System.out.println("Saved " + records.size() + " records to " + filePath.toString());
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}