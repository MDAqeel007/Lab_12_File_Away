import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileInspector {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path srcPath = Paths.get(workingDirectory.getAbsolutePath(), "src");

        if (Files.exists(srcPath)) {
            chooser.setCurrentDirectory(srcPath.toFile());
        } else {
            chooser.setCurrentDirectory(workingDirectory);
        }

        int choice = chooser.showOpenDialog(null);

        if (choice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            Path filePath = selectedFile.toPath();

            int lineCount = 0;
            int wordCount = 0;
            int charCount = 0;

            try (InputStream in = Files.newInputStream(filePath);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    System.out.println(line);

                    charCount += line.length();

                    String trimmed = line.trim();
                    if (!trimmed.isEmpty()) {
                        String[] words = trimmed.split("\\s+");
                        wordCount += words.length;
                    }
                }

                System.out.println("\n=== File Summary Report ===");
                System.out.println("File Name: " + selectedFile.getName());
                System.out.println("Number of Lines: " + lineCount);
                System.out.println("Number of Words: " + wordCount);
                System.out.println("Number of Characters: " + charCount);

            } catch (IOException e) {
                System.out.println("Error reading the file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("No file selected. Exiting program.");
        }
    }
}
