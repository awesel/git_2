import java.io.File;
import java.io.IOException;

public class Tester {
    public static void main(String args[]) throws IOException {
        // java.nio.file.Files.write(java.nio.file.Paths.get("andrew.txt"), "andrew is
        // cool".getBytes());
        // Blob.makeBlob("andrew.txt");
        // Tree.add("andrew.txt");

        // programmatically create a test file
        File file = new File("test.txt");
        file.createNewFile();

        // programmatically write to the test file
        // the custom method that writes to file
        // yours will be different
        Utility.writeToFile("a", "test.txt");

        // programmatically create a blob
        Blob blob = new Blob("test.txt");

        System.out.println("objects/" + Utility.sha1("a"));
    }
}
