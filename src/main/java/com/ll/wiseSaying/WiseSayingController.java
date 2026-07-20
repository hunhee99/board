package com.ll.wiseSaying;

import java.util.*;

public class WiseSayingController {
    public static void controller(WiseSayingService app, Scanner scanner, String command, String param){
        HashMap<String, String> paramsMap = parseParams(param);

        switch (command) {
            case "등록":
                addPost(app, scanner);
                break;
            case "목록":
                printPosts(app, paramsMap);
                break;
            case "삭제":
                removePost(app, Integer.parseInt(paramsMap.get("id")));
                break;
            case "수정":
                rewritePost(app, scanner, Integer.parseInt(paramsMap.get("id")));
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
    private static void printPosts(WiseSayingService app, HashMap<String, String> paramsMap){
        String keywordType = paramsMap.get("keywordType");
        String keyword = paramsMap.get("keyword");
        boolean isAuthor = keywordType.equals("author");

        List<Integer> keyList = new ArrayList<>(app.getPostsIdSet());
        Collections.sort(keyList, Collections.reverseOrder());

        int page = Integer.parseInt(paramsMap.get("page"));
        int start = (page - 1) * 5;
        int end = page * 5;
        int allPages = (int) Math.ceil((keyList.size()) / 5.0);

        StringBuilder sb = new StringBuilder();
        sb.append("페이지 :");
        for (int i = 1; i <= allPages; i++) {
            if (i == page){
                sb.append(" [" + i + "] /");
                continue;
            }
            sb.append(" " + i + " /");
        }


        // 검색어가 있을때만
        if ((!keywordType.equals(".*")) && (!keyword.equals(".*"))){
            System.out.println("----------------------");
            System.out.println("검색타입 : " + keywordType);
            System.out.println("검색어 : " + keyword);
            System.out.println("----------------------");
            keywordType = ".*" +  keywordType + ".*";
            keyword = ".*" +  keyword + ".*";
        }

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");



        for (int i = start; i < end && i < keyList.size(); i++) {
            WiseSaying tempPost = app.getPost(keyList.get(i));
            if ((tempPost.getAuthor().matches(keyword) && isAuthor) || (tempPost.getContent().matches(keyword) && (!isAuthor))) {
                System.out.println(tempPost.getId() + " / " + tempPost.getAuthor() + " / " + tempPost.getContent());
            }
        }
        System.out.println("----------------------");
        System.out.println(sb.toString().substring(0, sb.toString().length() - 2));
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

    // 파라미터 파싱
    private static HashMap<String, String> parseParams(String param){
        HashMap<String, String> parsed = new HashMap(){{
            put("page", "1");
            put("keywordType", ".*");
            put("keyword", ".*");
        }};

        String[] params = param.split("&");

        for (int i = 0; i < params.length; i++) {
            String[] keyValue = params[i].split("=");
            parsed.put(keyValue[0], keyValue[1]);
        }

        return parsed;
    }
}
