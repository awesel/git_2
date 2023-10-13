import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.*;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

public class CommitTest {
    /*
     * tests on two files, one commit
     */
    @Test
    public void testCommit_1commit() throws IOException {
        if (Files.exists(Paths.get("objects"))) {
            Tree.deleteDirectoryWalkTree(Paths.get("objects"));
        }

        String[] fileNames = { "1.txt", "2.txt", "3.txt" };
        String[] fileContents = { "1content", "2content", "3content" };
        for (String file : fileNames) {
            Files.deleteIfExists(Paths.get(file));
        }
        Files.deleteIfExists(Paths.get("Head"));
        Files.deleteIfExists(Paths.get("Tree"));

        for (int i = 0; i < fileNames.length; i++) {
            Utility.writeToFile(fileContents[i], fileNames[i]);
            Tree.add(fileNames[i]);
        }

        new Commit(null, "andrew", "messag");
        String answer = "a97523be3bca4155de8b631f67847a3f37e3e6ad\n\n\nandrew\nOct 13, 2023\nmessag";
        String commitFileText = Utility.readFile("objects/" + Utility.sha1(answer));

        assertEquals(answer, commitFileText); // (answer.equals(commitFileText));
    }

    /*
     * tests on two files, one folder two committs
     */
    @Test
    public void testCommit_2commit() throws IOException {
        if (Files.exists(Paths.get("objects"))) {
            Tree.deleteDirectoryWalkTree(Paths.get("objects"));
        }
        String[] fileNames = { "1.txt", "2.txt", "3.txt" };
        String[] fileContents = { "1content", "2content", "3content" };
        for (String file : fileNames) {
            Files.deleteIfExists(Paths.get(file));
        }
        Files.deleteIfExists(Paths.get("Head"));
        Files.deleteIfExists(Paths.get("Tree"));

        for (int i = 0; i < fileNames.length; i++) {
            Utility.writeToFile(fileContents[i], fileNames[i]);
            Tree.add(fileNames[i]);
        }

        Commit commit1 = new Commit(null, "andrew", "first commit (3 text files)");
        String answer = "a97523be3bca4155de8b631f67847a3f37e3e6ad\n\n\nandrew\nOct 13, 2023\nfirst commit (3 text files)";
        String commitFileText = Utility.readFile("objects/" + Utility.sha1(answer));
        String directoryPath = "testDirectory";
        Files.deleteIfExists(Paths.get("testDirectory/Tree"));
        if (Files.exists(Paths.get(directoryPath))) {
            Tree.deleteDirectoryWalkTree((Paths.get(directoryPath)));
        }
        Files.createDirectory(Paths.get(directoryPath));
        Tree.addDirectory(directoryPath);

        new Commit(commit1.getSha1(), "andrew", "second commit (3 text files + 1 folder)");
        String answer2 = "923cdc5b054aa2b9373ea156086b2f8c80aca69e\n" + //
                "2321065d2e5f536b9693e9a66f298e9429451a0e\n" + //
                "\n" + //
                "andrew\n" + //
                "Oct 13, 2023\n" + //
                "second commit (3 text files + 1 folder)";
        String commitFileText2 = Utility.readFile("objects/" + Utility.sha1(answer2));

        assertEquals(answer, commitFileText);
        assertEquals(answer2, commitFileText2);

    }

}
