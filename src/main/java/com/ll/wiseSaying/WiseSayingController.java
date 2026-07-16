package com.ll.wiseSaying;

import java.util.Scanner;

public class WiseSayingController {
    public static void controller(WiseSayingService app, Scanner scanner, String command, int param){
        switch (command) {
            case "등록":
                addPost(app, scanner);
                break;
            case "목록":
                printPosts(app);
                break;
            case "삭제":
                removePost(app, param);
                break;
            case "수정":
                rewritePost(app, scanner, param);
                break;
            case "빌드":
                build();
                break;
        }
    }

    // 등록
    private static void addPost(WiseSayingService app, Scanner scanner){
        System.out.print("명언 : ");
        String content = scanner.nextLine();

        System.out.print("작가 : ");
        String author = scanner.nextLine();

        int newPostId = app.createPost(content, author);
        System.out.println(newPostId + "번 명언이 등록되었습니다.");
    }

    // 목록
    private static void printPosts(WiseSayingService app){
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (int i = app.id; i > 0; i--) {
            if (app.existPost(i)) {
                System.out.println(i + " / " + app.getPost(i).getAuthor() + " / " + app.getPost(i).getContent());
            }
        }
    }

    // 삭제
    private static void removePost(WiseSayingService app, int param){
        if (app.existPost(param)) {
            app.deletePost(param);
            System.out.println(param + "번 명언이 삭제되었습니다.");
            return;
        }
        System.out.println(param + "번 명언은 존재하지 않습니다.");
    }

    // 수정
    private static void rewritePost(WiseSayingService app, Scanner scanner, int param){
        if (app.existPost(param)) {
            System.out.println("명언(기존) : " + app.getPost(param).getContent());
            System.out.print("명언 : ");
            String content = scanner.nextLine();

            System.out.println("명언(기존) : " + app.getPost(param).getAuthor());
            System.out.print("작가 : ");
            String author = scanner.nextLine();

            app.updatePost(content, author, param);
            return;
        }
        System.out.println(param + "번 명언은 존재하지 않습니다.");
    }

    // 빌드
    private static void build(){
        int maxId = WiseSayingRepository.getLastId();
        WiseSayingRepository.buildDataJson(maxId);
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}
