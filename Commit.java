import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class Commit {

    public Commit(String parentSha, String author, String message) throws IOException {
        checkCommitFile();

        String commit = "";
        new Tree();
        String treeSha1 = Utility.sha1(Utility.readFile("Tree"));

        String previous = "";

        if (parentSha != null) {
            previous = parentSha;
        }

        String next = "";
        String date = getDate();

        commit += treeSha1 + "\n" + previous + "\n" + next + "\n" + author + "\n" + date + "\n" + message;
        String shaOfCommit = Utility.sha1(commit);
        clearHeadFile();
        Utility.writeToFile(shaOfCommit, "Head");

        if (parentSha != null) {
            updatePrevious(previous, shaOfCommit);
        }

        Utility.writeToFile(commit, "Commit");
        new Blob("Commit");

        File commitFile = new File("Commit");
        commitFile.delete();

    }

    /*
     * inserts shaToAdd in the third line of ./objects/previousSha
     * I used this guide:
     * https://www.digitalocean.com/community/tutorials/java-read-file-line-by-line
     */
    private static void updatePrevious(String previousSha, String shaToAdd) throws IOException {
        Path path = Paths.get("./objects", previousSha);
        List<String> lines = Files.readAllLines(path);
        lines.add(2, shaToAdd);
        Files.write(path, lines);
    }

    private static void checkCommitFile() throws IOException {
        Path pathToTree = Paths.get("Commit");
        if (!Files.exists(pathToTree))
            Files.createFile(pathToTree);
    }

    private static void clearHeadFile() throws IOException {
        Path pathToHead = Paths.get("Head");
        if (!Files.exists(pathToHead))
            Files.createFile(pathToHead);
        else {
            File headFile = new File("Head");
            headFile.delete();
            Files.createFile(pathToHead);
        }
    }

    /*
     * This is a method which gives a formatted date.
     * I used this guide
     * https://www.digitalocean.com/community/tutorials/java-simpledateformat-java-
     * date-format
     */
    private static String getDate() {
        Date currentDate = new Date();

        SimpleDateFormat dataFormat = new SimpleDateFormat("MMM dd, yyyy");

        String formattedDate = dataFormat.format(currentDate);
        return formattedDate;

    }
}
