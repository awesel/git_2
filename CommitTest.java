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
     * 
     */
    @Test
    public void testCommit() throws IOException {
        String answer = "da39a3ee5e6b4b0d3255bfef95601890afd80709\n\n\nandrew\n"
                + Commit.getDate() + "\nmessag";
        String hashOfTheDay = Utility.sha1(answer);
        File testFile = new File("objects/" + hashOfTheDay);
        testFile.delete();

        new Commit(null, "andrew", "messag");

        String commitFileText = Utility.readFile("objects/" + hashOfTheDay);

        assertEquals(answer, commitFileText); // (answer.equals(commitFileText));

    }

    @AfterEach
    void tearDown() throws IOException {

    }

}
