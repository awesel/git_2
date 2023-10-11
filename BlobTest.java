import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BlobTest {
    private static final Logger LOGGER = Logger.getLogger(BlobTest.class.getName());

    /*
     * This is copied from the Day 9 Notion page. I did not write this myself.
     */
    @Test
    void testCreateFolder() throws IOException {
        // programmatically create a test file
        File file = new File("test.txt");
        file.createNewFile();

        // programmatically write to the test file
        // the custom method that writes to file
        // yours will be different
        Utility.writeToFile("hello world", "test.txt");

        // programmatically create a blob
        Blob blob = new Blob("test.txt");

        // Blob should create an objects folder.
        File folder = new File("objects");

        // does the folder exist?
        assertTrue(folder.exists());
    }

    /*
     * This method test whether the blob is created.
     */
    @Test
    void testMakeBlob() throws IOException {
        // programmatically create a test file
        File file = new File("test.txt");
        file.createNewFile();

        // programmatically write to the test file
        // the custom method that writes to file
        // yours will be different
        Utility.writeToFile("a", "test.txt");

        // programmatically create a blob
        Blob blob = new Blob("test.txt");
        LOGGER.log(Level.INFO, "Real blob name: " + blob.getSha1());

        String expectedBlobName = Utility.sha1("a");
        LOGGER.log(Level.INFO, "Expected blob name: " + expectedBlobName);

        // Blob should create an objects folder.
        File expectedBlobFile = new File("objects/" + expectedBlobName);

        // does the Blob exist with the correct name?
        assertTrue(expectedBlobFile.exists());
    }

    @AfterEach
    void tearDown() {
        File testFile = new File("test.txt");
        testFile.delete();
    }
}
