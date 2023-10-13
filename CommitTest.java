import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

public class CommitTest {
    /*
     * To get this test to pass you must always replace the date with whatever day
     * it is, and redo the hash. Sorry about that.
     * Just run the Tester class to see!
     */
    @Test
    public void testCommit() throws IOException {
        String hashOfTheDay = "2318b8f82ddccd919f4ec82531fc4f922f5ebee5"; // this is the hash for oct 12.
        File testFile = new File("objects/" + hashOfTheDay);
        testFile.delete();

        String answer = "da39a3ee5e6b4b0d3255bfef95601890afd80709\n\n\nandrew\n"
                + "Oct 12, 2023" + "\nmessag";
        // Remeber to replace oct 12 w/ whatever day it is now

        new Commit(null, "andrew", "messag");

        String commitFileText = Utility.readFile("objects/" + hashOfTheDay);

        assertEquals(answer, commitFileText); // (answer.equals(commitFileText));

    }

    @AfterEach
    void tearDown() throws IOException {

    }

}
