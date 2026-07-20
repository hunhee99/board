package com.choi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Scanner;

public class TestUtil {
    private static PrintStream ORIGINAL_OUT = System.out;
    private static PrintStream CURRENT_OUT = System.out;
    private static final String DB_PATH = "db";
    private static final String DB_JSON_PATH = DB_PATH + "/wiseSaying/data.json";
    public static Scanner genScanner(String input) {
        return new Scanner(input);
    }

    public static ByteArrayOutputStream setOutToByteArray() {

        ORIGINAL_OUT = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        CURRENT_OUT = printStream;

        return outputStream;
    }

    public static void clearSetOutToByteArray(ByteArrayOutputStream outputStream) throws IOException {
        System.setOut(ORIGINAL_OUT);
        outputStream.close();
    }

    public static void dbFolderClear() {
        File folder = new File(DB_PATH);
        deleteRecursive(folder);
    }

    private static void deleteRecursive(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if  (children != null) {
                for (File child : children) {
                    deleteRecursive(child);
                }
            }
        }
        file.delete();
    }

    public static String readDataJson() throws IOException {
        File file = new File(DB_JSON_PATH);
        return Files.readString(file.toPath()).trim();
    }
}
