import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class TreeTest {
    private static final Logger LOGGER = Logger.getLogger(TreeTest.class.getName());

    @Test
    void testCreateFile() throws IOException {
        File file = new File("test.txt");
        file.createNewFile();

        Utility.writeToFile("hello world", "test.txt");

        Tree.add("test.txt");

        File treeFile = new File("Tree");

        // does the file exist?
        assertTrue(treeFile.exists());
    }

    /*
     * This method test whether adding a file to the Tree creates a blob correctly.
     */
    @Test
    void testMakeBlob() throws IOException {
        File file = new File("test.txt");
        file.createNewFile();

        Utility.writeToFile("a", "test.txt");

        Tree.add("test.txt");

        String expectedBlobName = Utility.sha1("a");

        File expectedBlobFile = new File("objects/" + expectedBlobName);

        assertTrue(expectedBlobFile.exists());
    }

    /*
     * This method tests whether the tree class adds entries properly.
     */
    @Test
    void testAddEntry() throws IOException {
        LOGGER.log(Level.INFO, "logga");

        File file = new File("test.txt");
        file.createNewFile();

        Utility.writeToFile("a", "test.txt");

        Tree.add("test.txt");

        String expectedEntry = "blob : " + Utility.sha1("a") + " : test.txt" + "\n";
        LOGGER.log(Level.INFO, "expectd: " + expectedEntry);
        String realEntry = Utility.readFile("Tree");
        LOGGER.log(Level.INFO, "real: " + realEntry);

        assertTrue(expectedEntry.equals(realEntry));

    }

    /*
     * This tests the newline mechanics.
     */
    @Test
    void testTwoEntries() throws IOException {
        LOGGER.log(Level.INFO, "logga");

        File file = new File("test.txt");
        file.createNewFile();

        Utility.writeToFile("a", "test.txt");

        Tree.add("test.txt");
        Tree.add("test.txt");

        String expectedEntry = "blob : " + Utility.sha1("a") + " : test.txt" + "\n" + "blob : " + Utility.sha1("a")
                + " : test.txt" + "\n";
        LOGGER.log(Level.INFO, "expectd: " + expectedEntry);
        String realEntry = Utility.readFile("Tree");
        LOGGER.log(Level.INFO, "real: " + realEntry);

        assertEquals(expectedEntry, realEntry); // (expectedEntry.equals(realEntry));

    }

    /*
     * Tests remove mechanics.
     */
    @Test
    void testRemove() throws IOException {
        LOGGER.log(Level.INFO, "logga");

        File file = new File("test.txt");
        file.createNewFile();
        Utility.writeToFile("a", "test.txt");

        File file2 = new File("test2.txt");
        file2.createNewFile();
        Utility.writeToFile("a2", "test2.txt");

        Tree.add("test.txt");
        Tree.add("test2.txt");

        Tree.remove("test2.txt");

        String expectedEntry = "blob : " + Utility.sha1("a") + " : test.txt" + "\n";
        LOGGER.log(Level.INFO, "expectd: " + expectedEntry);
        String realEntry = Utility.readFile("Tree");
        LOGGER.log(Level.INFO, "real: " + realEntry);

        assertTrue(expectedEntry.equals(realEntry));

    }

    @AfterEach
    void tearDown() {
        File testFile = new File("test.txt");
        testFile.delete();
        File test2File = new File("test2.txt");
        test2File.delete();
        File treeFile = new File("Tree");
        treeFile.delete();
    }
}
