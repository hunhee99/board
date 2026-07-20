package com.ll.wiseSaying;

import java.util.Scanner;

public class App {
    private Scanner scanner;

    public App(Scanner scanner){
        this.scanner = scanner;
    }


    public void run() {
        WiseSayingService app = new WiseSayingService();
        String input;

        System.out.println("== 명언 앱 ==");

        while (true) {
            // 입력
            System.out.print("명령) ");
            input = this.scanner.nextLine();
            int inputLength = input.length();
            String param = "page=1";

            String command = input.substring(0, 2);
            if (input.contains("?")){
                param = input.substring(input.indexOf("?") + 1, inputLength);
            }

            if (command.equals("종료")) {
                this.scanner.close();
                return;
            }

            // 예외 처리를 아직 모르겠음
            try {
                WiseSayingController.controller(app, this.scanner, command, param);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

    }

}


