package se.rifr.tools.common;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class FileIOSupport {

    private enum fileTypeKind { STANDARD_IO, FILE_IO };
    private fileTypeKind fileType = fileTypeKind.STANDARD_IO;
    private String fileName;

    private String errorFileName = "ErrorReport.txt";

    private FileWriter outFile = null;
    private FileWriter errorFile = null;
    private BufferedReader inFile  = null;
    private Scanner scan       = null;

    //Runtime runtime = Runtime.getRuntime();

    public FileIOSupport(String fileName){
         if (!(fileName == null || fileName.trim().equals(""))) {
             this.fileName = fileName;
             this.fileType = fileTypeKind.FILE_IO;
         }
    }

    //private boolean isOpened   = false;

    public void print (String str) throws IOException {

        if (fileType.equals(fileTypeKind.STANDARD_IO))
            System.out.print(str);
        else
            outFile.write(str);
    }

    public void println (String str) throws IOException {
        if (fileType.equals(fileTypeKind.STANDARD_IO))
            System.out.println(str);
        else
            outFile.write(str + "\r\n");
    }

    public static void errorReport (String str) {

        System.out.println(consoleColors.RED + "ERROR: " + str + consoleColors.RESET);

        //java.nio.file.Files.write();

        errorScreen ("ERROR: " +str);
    }



    public void create() throws IOException {

        if (fileType.equals(fileTypeKind.STANDARD_IO)) {
            System.out.println("Improper use of TextIO.Create ");
            return;
        }
        outFile = new FileWriter(fileName);
    }

    public void delete() throws IOException {
        if (fileType.equals(fileTypeKind.STANDARD_IO)) {
            System.out.println("Improper use of TextIO.Delete ");
            return;
        }
        if (inFile != null) {
            outFile.flush();
            outFile.close();
        }
    }

    public boolean isOpened() throws IOException {
        if (fileType.equals(fileTypeKind.STANDARD_IO)) {
            System.out.println("Improper use of TextIO.IsOpened command ");
            return true;
        }
        return inFile != null;
    }

    public void open() throws IOException {
        if (fileType.equals(fileTypeKind.STANDARD_IO)) {
            System.out.println("Improper use of TextIO.Open ");
            return;
        }
        if (outFile == null)
            outFile = new FileWriter(fileName);
    }

    public void close() throws IOException {

        if (outFile != null)
            outFile.close();

        if (inFile != null)
            inFile.close();

        if (scan != null)
            scan.close();

    }

    public void write(String str) throws IOException {
        if (fileType.equals(fileTypeKind.STANDARD_IO)) {
            System.out.print(str);
            return;
        }
        outFile.write(str);
    }

    public void append(String str) throws IOException {
        if (fileType.equals(fileTypeKind.STANDARD_IO)) {
            System.out.println("Improper use of TextIO.Append ");
            return;
        }
        outFile.append(str);
    }

    public String readLine() throws IOException {

        if (fileType.equals(fileTypeKind.STANDARD_IO))
            if (scan == null) {
                scan = new Scanner(System.in);
                return scan.nextLine();
            }
        else {
            if (inFile == null)
                inFile = new BufferedReader (new FileReader(fileName));

            return inFile.readLine();
        }
        return null;
    }

    public List<String> readAllLines() throws IOException {

        if (fileType.equals(fileTypeKind.STANDARD_IO)) {

            System.out.println("Improper use of TextIO.ReadAllLine ");
            return null;
        }
        return java.nio.file.Files.readAllLines
                (new File(fileName).toPath(), StandardCharsets.UTF_8);

    }

    public void flush () throws IOException {

        if (fileType.equals(fileTypeKind.STANDARD_IO))
            System.out.print("\r\n");
        else
            if (outFile != null)
                outFile.flush();
    }

    public static void errorScreen(String errorText) {

        try {
            new ProcessBuilder("cmd", "/c", "start cmd.exe /K \"CLS &&color fc &&echo " +
                    errorText + "&&ping localhost &&exit \"").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.print("Exception " + e + " in ErrorScreen");
        }
    }


    public static void clearScreen () throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public static class consoleColors {
        // Reset
        public static final String RESET = "\033[0m";  // Text Reset

        // Regular Colors
        public static final String BLACK = "\033[0;30m";   // BLACK
        public static final String RED = "\033[0;31m";     // RED
        public static final String GREEN = "\033[0;32m";   // GREEN
        public static final String YELLOW = "\033[0;33m";  // YELLOW
        public static final String BLUE = "\033[0;34m";    // BLUE
        public static final String PURPLE = "\033[0;35m";  // PURPLE
        public static final String CYAN = "\033[0;36m";    // CYAN
        public static final String WHITE = "\033[0;37m";   // WHITE

        // Bold
        public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
        public static final String RED_BOLD = "\033[1;31m";    // RED
        public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
        public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
        public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
        public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
        public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
        public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

        // Underline
        public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
        public static final String RED_UNDERLINED = "\033[4;31m";    // RED
        public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
        public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
        public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
        public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
        public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
        public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

        // Background
        public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
        public static final String RED_BACKGROUND = "\033[41m";    // RED
        public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
        public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
        public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
        public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
        public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
        public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

        // High Intensity
        public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
        public static final String RED_BRIGHT = "\033[0;91m";    // RED
        public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
        public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
        public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
        public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
        public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
        public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

        // Bold High Intensity
        public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
        public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
        public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
        public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
        public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
        public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
        public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
        public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

        // High Intensity backgrounds
        public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
        public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
        public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
        public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
        public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
        public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
        public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
        public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE
    }
}
