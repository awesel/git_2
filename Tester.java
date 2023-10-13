import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class Tester {
    public static void main(String args[]) throws IOException {
        // java.nio.file.Files.write(java.nio.file.Paths.get("andrew.txt"), "andrew is
        // cool".getBytes());
        // Blob.makeBlob("andrew.txt");
        // Tree.add("andrew.txt");

        // programmatically create a test file
        // File file = new File("test.txt");
        // file.createNewFile();

        // programmatically write to the test file
        // the custom method that writes to file
        // yours will be different
        // Utility.writeToFile("a", "test.txt");

        // programmatically create a blob
        // new Blob("test.txt");

        // System.out.println("objects/" + Utility.sha1("a"));
        // new Commit(null, "andrew", "messag");
        // String directoryPath = "testDirectory";
        // String[] fileNames = { "1.txt", "2.txt", "3.txt" };
        // String[] fileContents = { "1content", "2content", "3content" };

        // File treeFile = new File("Tree");
        // treeFile.delete();

        // for (String fileName : fileNames) {
        // Files.deleteIfExists(Paths.get(directoryPath, fileName));
        // }

        // Files.deleteIfExists(Paths.get(directoryPath));
        // Files.createDirectory(Paths.get(directoryPath));

        // for (int i = 0; i < 3; i++) {
        // Utility.writeToFile(fileContents[i], directoryPath + "/" + fileNames[i]);
        // }

        // Tree.addDirectory(directoryPath);

        String directoryPath = "testDirectory";
        String innerDirectoryPath = directoryPath + "/innerTest";
        String[] fileNames = { "1.txt", "2.txt", "3.txt", "innerTest/4.txt", "innerTest/5.txt" };
        String[] fileContents = { "1content", "2content", "3content", "4content", "5content" };

        File treeFile = new File("Tree");
        treeFile.delete();

        for (String fileName : fileNames) {
            Files.deleteIfExists(Paths.get(directoryPath, fileName));
        }

        Files.deleteIfExists(Paths.get(directoryPath));
        Files.createDirectory(Paths.get(directoryPath));
        Files.createDirectory(Paths.get(innerDirectoryPath)); // Create innerTest directory

        for (int i = 0; i < fileNames.length; i++) {
            Utility.writeToFile(fileContents[i], directoryPath + "/" + fileNames[i]);
        }

        Tree.addDirectory(directoryPath);

    }
}
