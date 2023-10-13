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
        while (br.ready()) {
            line = br.readLine();
            String[] fullLine = line.split(" : ");

            if ("blob".equals(fullLine[0].trim())) {
                String content = Utility.readFile("./objects/" + fullLine[1].trim());
                Utility.writeToFile(content, fullLine[2].trim());

            } else if ("tree".equals(fullLine[1].trim())) {
                File newFolder = new File(fullLine[2].trim());
                if (!newFolder.exists()) {
                    newFolder.mkdir();
                }
                checkoutHelper(fullLine[1].trim());
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
