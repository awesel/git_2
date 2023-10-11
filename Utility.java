import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Utility {
    // TAKEN FROM STACK
    public static String sha1(String password) {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }

    // TAKEN FROM STACK
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String readFile(String fileName) {
        File file = new File(fileName);
        String currentString = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                char nextChar = (char) reader.read();
                currentString += nextChar;

            }
            reader.close();
        } catch (IOException ex) {
            System.out.println("nah");
        }
        return currentString;
    }

    public static void writeToFile(String content, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
