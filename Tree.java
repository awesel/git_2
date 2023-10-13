import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
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
    // public static void add(String fileName) throws IOException {
    // String fileStart = fileName.substring(0, 4);
    // System.out.println("fileStart: " + fileStart);
    // String toCompare = "tree";
    // if (toCompare.equals(fileStart)) {
    // System.out.println("working");
    // checkTreeFile();
    // String folderName = getAfterSlash(fileName);
    // String toAdd = getBeforeSecondColon(fileName) + " " + folderName;
    // Utility.writeToFile(toAdd, "Tree");
    // Utility.writeToFile("\n", "Tree");
    // } else {
    // System.out.println("not working");

    // checkTreeFile();

    // new Blob(fileName);

    // String fileContent = Utility.readFile(fileName);
    // System.out.println(fileContent);
    // String newEntryForTree = "blob : " + Utility.sha1(fileContent) + " : "
    // + getAfterSlash(fileName);
    // Utility.writeToFile(newEntryForTree, "Tree");
    // Utility.writeToFile("\n", "Tree");
    // }
    // }

    public static void add(String fileName) throws IOException {
        String fileStart = fileName.substring(0, 4);
        // System.out.println("fileStart: " + fileStart);
        System.out.println("fileName: " + fileName);
        String toCompare = "tree";

        if (toCompare.equals(fileStart)) {
            System.out.println("working");
            checkTreeFile();
            String path = getAfterSecondColon(fileName);
            String folderName = getAfterSlash(fileName);
            // System.out.println(folderName);
            File folderFile = new File(path);
            String whereToTree = folderFile.getParent();
            String toAdd = fileName;
            if (getAfterSlash(fileName) != null)
                toAdd = getBeforeSecondColon(fileName) + " " + getAfterSlash(fileName);

            if (whereToTree != null) {
                whereToTree = whereToTree.trim();
                // System.out.println(whereToTree + "/Tree");
                Utility.writeToFile(toAdd, whereToTree + "/Tree");
                Utility.writeToFile("\n", whereToTree + "/Tree");
            } else {
                Utility.writeToFile(toAdd, "Tree");
                Utility.writeToFile("\n", "Tree");
            }

        } else {
            String folderPath;
            File file = new File(fileName);
            String parentPath = file.getParent(); // this method gets the folder that the file is in
            folderPath = parentPath + "/";
            if (parentPath != null) {
                // System.out.println("not working");
                checkTreeFile();
                new Blob(fileName);
                String fileContent = Utility.readFile(fileName);
                System.out.println(fileContent);
                String newEntryForTree = "blob : " + Utility.sha1(fileContent) + " : " + getAfterSlash(fileName);
                Utility.writeToFile(newEntryForTree, folderPath + "Tree");
                Utility.writeToFile("\n", folderPath + "Tree");
            } else {
                new Blob(fileName);
                String fileContent = Utility.readFile(fileName);
                String newEntryForTree = "blob : " + Utility.sha1(fileContent) + " : " + fileName;
                Utility.writeToFile(newEntryForTree, "Tree");
                Utility.writeToFile("\n", "Tree");

            }
        }
    }

    public static void printContentsOfFolder(String folderPath) {
        File folder = new File(folderPath);

        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            System.out.println(file.getName());
        }

    }

    public static String getBeforeSecondColon(String input) {
        int dummy = input.indexOf(':');

        int indx = input.indexOf(':', dummy + 1);

        return input.substring(0, indx + 1);
    }

    public static String getAfterSecondColon(String input) {
        int dummy = input.indexOf(':');

        int indx = input.indexOf(':', dummy + 1);

        return input.substring(indx + 1, input.length());
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
            addDirectory(folder);
        }

        Path treePath = Paths.get(directoryPath + "/Tree");
        if (!Files.exists(treePath)) {
            Files.createFile(treePath);
        }
        new Blob(directoryPath + "/Tree");
        String shaOfTree = Utility.sha1(Utility.readFile(directoryPath + "/Tree"));
        // String afterSlash = getAfterSlash(directoryPath);
        // System.out.println("Directory path: " + directoryPath);
        // System.out.println("After slash: " + afterSlash);
        String toAdd = "tree : " + shaOfTree + " : " + directoryPath;
        add(toAdd);

        return shaOfTree;
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

    /*
     * I got this method from online. This is NOT my own work
     * https://softwarecave.org/2018/03/24/delete-directory-with-contents-in-java/
     */
    public static void deleteDirectoryWalkTree(Path path) throws IOException {
        FileVisitor visitor = new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null) {
                    throw exc;
                }
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        };
        Files.walkFileTree(path, visitor);
    }
}
