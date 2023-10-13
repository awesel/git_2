import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

public class TreeTest_Day11 {
    final String directoryPath = "./testDirectory";
    final String[] fileNames = { "1.txt", "2.txt", "3.txt" };
    final String[] fileContents = { "1content", "2content", "3content" };

    @Test
    public void testAddDirectory() throws IOException {
        File treeFile = new File("Tree");
        treeFile.delete();

        for (String fileName : fileNames) {
            Files.deleteIfExists(Paths.get(directoryPath, fileName));
        }

        Files.deleteIfExists(Paths.get(directoryPath));
        Files.createDirectory(Paths.get(directoryPath));

        for (int i = 0; i < 3; i++) {
            Utility.writeToFile(fileContents[i], directoryPath + "/" + fileNames[i]);

        }

        Tree.addDirectory(directoryPath);

        String answer = "blob : 626a3f6889703b8971d6fb78eb2c24d4723acaa1 : 3.txt\nblob : 881e9a35256b3a94409c2b2d70a40f3c9fea8070 : 2.txt\nblob : 5cea2f76b1a4383f05a835bfd088190dc3ce12fd : 1.txt\n";
        String real = Utility.readFile("Tree");
        assertEquals(answer, real);

    }

    @Test
    public void testAddDirectoryHard() throws IOException {
        String directoryPath = "testDirectory";
        String innerDirectoryPath = directoryPath + "/innerTest";
        String[] fileNames = { "1.txt", "2.txt", "3.txt", "innerTest/4.txt", "innerTest/5.txt" };
        String[] fileContents = { "1content", "2content", "3content", "4content", "5content" };

        File treeFile = new File("Tree");
        treeFile.delete();

        for (String fileName : fileNames) {
            Files.deleteIfExists(Paths.get(directoryPath, fileName));
        }

        Files.deleteIfExists(Paths.get(innerDirectoryPath));

        Files.deleteIfExists(Paths.get(directoryPath));
        Files.createDirectory(Paths.get(directoryPath));
        Files.createDirectory(Paths.get(innerDirectoryPath)); // Create innerTest directory

        for (int i = 0; i < fileNames.length; i++) {
            Utility.writeToFile(fileContents[i], directoryPath + "/" + fileNames[i]);
        }

        Tree.addDirectory(directoryPath);

        String answer = "blob : 626a3f6889703b8971d6fb78eb2c24d4723acaa1 : 3.txt\nblob : 881e9a35256b3a94409c2b2d70a40f3c9fea8070 : 2.txt\nblob : 5cea2f76b1a4383f05a835bfd088190dc3ce12fd : 1.txt\nblob : 5740b5e1d0b8ef02f56be694441c9b125c26ef2c : 5.txt\nblob : ea5e30ea32338c509c8cf32d7df755c5640bd120 : 4.txt\ntree : 1e7e0ebfff5265f6422e9ff88cac302883cd2c2c : innerTest\n";
        String real = Utility.readFile("Tree");
        assertEquals(answer, real);
    }

}
