package com.ll.wiseSaying;

import java.util.Scanner;

public class App {
    public static void run() {

        Scanner scanner = new Scanner(System.in);
        WiseSayingService app = new WiseSayingService();
        String input;

        System.out.println("== 명언 앱 ==");

        while (true) {
            // 입력
            System.out.print("명령) ");
            input = scanner.nextLine();
            int inputLength = input.length();
            int param = -1;

            String command = input.substring(0, 2);
            if ((inputLength) > 2 && (input.contains("="))){
                int paramStartIdx = input.indexOf('=') + 1;
                param = Integer.parseInt(input.substring(paramStartIdx, inputLength));
            }

            if (command.equals("종료")) {
                scanner.close();
                return;
            }

            // 예외 처리를 아직 모르겠음
            try {
                WiseSayingController.controller(app, scanner, command, param);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

    }

}


