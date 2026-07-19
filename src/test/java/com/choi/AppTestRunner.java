package com.choi;

import com.ll.wiseSaying.App;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class AppTestRunner {
    public static String run(String input){
        Scanner scanner = TestUtil.genScanner(input);

        ByteArrayOutputStream outputStream = TestUtil.setOutToByteArray();

        new App(scanner).run();

        return outputStream.toString();
    }
}
