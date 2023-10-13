import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Git {
    /*
     * This method reconstructs the files from that commit stage.
     */
    public static void checkout(String shaOfCommit) throws IOException {
        String shaOfTree = readFirstLine(shaOfCommit);
        checkoutHelper(shaOfTree);
    }

    public static void checkoutHelper(String shaOfTree) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("./objects/" + shaOfTree));

        String line;
        while ((line = br.readLine()) != null) {
            String[] fullLine = line.split(" : ");

            String typeOfFile = fullLine[0].trim();
            String sha1OfFile = fullLine[1].trim();
            String fileName = fullLine[2].trim();

            if ("blob".equals(typeOfFile)) {
                String content = Utility.readFile("./objects/" + sha1OfFile);
                Utility.writeToFile(content, fileName);
            } else if ("tree".equals(typeOfFile)) {
                File newFolder = new File(fileName);
                if (!newFolder.exists()) {
                    newFolder.mkdir();
                }
                checkoutHelper(sha1OfFile);
            }
        }
    }

    public static String readFirstLine(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String tempLine = br.readLine();
        br.close();

        return tempLine;
    }
}
