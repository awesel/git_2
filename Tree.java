import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tree {
    public Tree() throws IOException {
        checkTreeFile();
    }

    private static void checkTreeFile() throws IOException {
        Path pathToTree = Paths.get("Tree");
        if (!Files.exists(pathToTree))
            Files.createFile(pathToTree);
    }

    /*
     * Given a file, this method makes a blob and then makes an entry for the blob
     * in the tree.
     */
    public static void add(String fileName) throws IOException {
        checkTreeFile();

        new Blob(fileName);

        String newEntryForTree = "blob : " + Utility.sha1("a") + " : " + fileName;
        Utility.writeToFile(newEntryForTree, "Tree");
        Utility.writeToFile("\n", "Tree");

        new Blob("Tree");
    }

    /*
     * Given a file, this removes its entry from the Tree file.
     */
    public static void remove(String fileName) throws IOException {
        File inputFile = new File("Tree");
        File tempFile = new File("Tree_temp");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (!currentLine.contains(fileName)) {
                writer.write(currentLine);
                writer.write("\n");
            }
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);

        reader.close();
        writer.close();

        new Blob("Tree");

    }
}
