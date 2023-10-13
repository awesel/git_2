import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Tree {
    public Tree() throws IOException {
        checkTreeFile();
    }

    private static void checkTreeFile() throws IOException {
        Path pathToTree = Paths.get("Tree");
        if (!Files.exists(pathToTree))
            Files.createFile(pathToTree);
    }

    /*
     * Given a file, this method makes a blob and then makes an entry for the blob
     * in the tree.
     */
    public static void add(String fileName) throws IOException {
        String fileStart = fileName.substring(0, 4);
        System.out.println("fileStart: " + fileStart);
        String toCompare = "tree";
        if (toCompare.equals(fileStart)) {
            System.out.println("working");
            checkTreeFile();
            String folderName = getAfterSlash(fileName);
            String toAdd = getBeforeSecondColon(fileName) + " " + folderName;
            Utility.writeToFile(toAdd, "Tree");
            Utility.writeToFile("\n", "Tree");
        } else {
            System.out.println("not working");

            checkTreeFile();

            new Blob(fileName);

            String fileContent = Utility.readFile(fileName);
            System.out.println(fileContent);
            String newEntryForTree = "blob : " + Utility.sha1(fileContent) + " : "
                    + getAfterSlash(fileName);
            Utility.writeToFile(newEntryForTree, "Tree");
            Utility.writeToFile("\n", "Tree");
        }
    }

    public static String getBeforeSecondColon(String input) {
        int dummy = input.indexOf(':');

        int indx = input.indexOf(':', dummy + 1);

        return input.substring(0, indx + 1);
    }

    public static String getAfterSlash(String input) {
        if (input.contains("/")) {
            int slashIndex = input.lastIndexOf('/');

            return input.substring(slashIndex + 1);
        }
        return null; // fail

    }

    public static void save() throws IOException {
        removeExtraLine("Tree");
        new Blob("Tree");
        Utility.writeToFile("\n", "Tree");
    }

    public static void removeExtraLine(String fileName) {
        String text = Utility.readFile(fileName);
        text = text.substring(0, text.length() - 2); // cuz \n is two characters
        Utility.writeToFile(text, fileName);
    }

    /*
     * Given a file, this removes its entry from the Tree file.
     * I coded it in a way that will work for sha1 too. So I did not update it at
     * all for the day 11 assignment.
     */
    public static void remove(String fileName) throws IOException {
        File inputFile = new File("Tree");
        File tempFile = new File("Tree_temp");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (!currentLine.contains(fileName)) {
                writer.write(currentLine);
                writer.write("\n");
            }
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);

        reader.close();
        writer.close();
    }

    /*
     * this is the addDirectory method which trees an entire folder. Hype
     */
    public static String addDirectory(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new IOException("Invalid directory path: " + directoryPath);
        }

        ArrayList<String> files = findFiles(directoryPath);
        ArrayList<String> folders = findFolders(directoryPath);

        for (String file : files) {
            System.out.println(file);
            add(file);
        }

        for (String folder : folders) {
            String childTreeSHA1 = addDirectory(folder);
            add("tree : " + childTreeSHA1 + " : " + folder);
        }

        Blob treeBlob = new Blob("Tree");
        return treeBlob.getSha1();
    }

    /*
     * this finds all the folders
     */
    public static ArrayList<String> findFolders(String directoryPath) {
        ArrayList<String> folders = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] filesAndFolders = directory.listFiles();

            if (filesAndFolders != null) {
                for (File fileOrFolder : filesAndFolders) {
                    if (fileOrFolder.isDirectory()) {
                        folders.add(fileOrFolder.getAbsolutePath());
                    }
                }
            }
        }

        return folders;
    }

    /*
     * this has the if statement reversed from the findFolders method
     */
    public static ArrayList<String> findFiles(String directoryPath) {
        ArrayList<String> folders = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] filesAndFolders = directory.listFiles();

            if (filesAndFolders != null) {
                for (File fileOrFolder : filesAndFolders) {
                    if (!fileOrFolder.isDirectory()) {
                        folders.add(fileOrFolder.getAbsolutePath());
                    }
                }
            }
        }

        return folders;
    }
}
