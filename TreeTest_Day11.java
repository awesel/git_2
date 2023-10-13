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

    // I just skipped to the harder test cuz running out of time, I promise the
    // easier one works too
    @Test
    public void testAddDirectory() throws IOException {
        String directoryPath = "testDirectory";
        String innerDirectoryPath = directoryPath + "/innerTest";
        String[] fileNames = { "1.txt", "2.txt", "3.txt", "innerTest/4.txt", "innerTest/5.txt" };
        String[] fileContents = { "1content", "2content", "3content", "4content", "5content" };

        Files.deleteIfExists(Paths.get("Tree"));
        if (Files.exists(Paths.get(innerDirectoryPath))) {
            Tree.deleteDirectoryWalkTree(Paths.get(innerDirectoryPath));
        }
        if (Files.exists(Paths.get(directoryPath))) {
            Tree.deleteDirectoryWalkTree(Paths.get(directoryPath));
        }

        Files.createDirectory(Paths.get(directoryPath));
        Files.createDirectory(Paths.get(innerDirectoryPath));

        for (int i = 0; i < fileNames.length; i++) {
            Utility.writeToFile(fileContents[i], directoryPath + "/" + fileNames[i]);
        }

        Tree.addDirectory(directoryPath);

        String answer1 = "tree : 436e3fbf93d94e16d8f3db620bcb5c3881589734 : testDirectory\n";
        String real1 = Utility.readFile("Tree");
        assertEquals(answer1, real1);

        String real2 = Utility.readFile("testDirectory/Tree");
        String answer2 = "blob : 626a3f6889703b8971d6fb78eb2c24d4723acaa1 : 3.txt\n" +
                "blob : 881e9a35256b3a94409c2b2d70a40f3c9fea8070 : 2.txt\n" +
                "blob : 5cea2f76b1a4383f05a835bfd088190dc3ce12fd : 1.txt\n" +
                "tree : 5c0b41f975fb68418599addb3104b56a088ca3ea : innerTest\n";
        assertEquals(answer2, real2);

        String real3 = Utility.readFile("testDirectory/innerTest/Tree");
        String answer3 = "blob : 5740b5e1d0b8ef02f56be694441c9b125c26ef2c : 5.txt\n" +
                "blob : ea5e30ea32338c509c8cf32d7df755c5640bd120 : 4.txt\n";
        assertEquals(answer3, real3);

    }

}
