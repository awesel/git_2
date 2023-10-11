import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// ~/Library/Java/JavaVirtualMachines/openjdk-17.0.2
public class Blob {
    static String blobString;
    static String sha1;

    public Blob(String filename) throws IOException {
        makeBlob(filename);

    }

    /*
     * This method should be run at the top of all of the other Blob methods just to
     * make sure that the object
     * folder exists.
     */
    private static void checkObjFolder() throws IOException {
        Path pathToObjects = Paths.get("./objects");
        if (!Files.exists(pathToObjects))
            Files.createDirectory(pathToObjects);
    }

    public String getSha1() {
        return sha1;
    }

    public String getBlobString() {
        return blobString;
    }

    /*
     * This method makes the blob in the objects folder for a given file.
     */
    public static void makeBlob(String fileName) throws IOException {
        checkObjFolder();
        blobString = Utility.readFile(fileName);
        sha1 = Utility.sha1(blobString);
        String sha1_fileName = "./objects/" + sha1;
        Utility.writeToFile(blobString, sha1_fileName);
    }
}