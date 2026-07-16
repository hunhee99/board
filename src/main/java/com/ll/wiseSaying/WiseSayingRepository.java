package com.ll.wiseSaying;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

class WiseSayingRepository {
    private static final String dirAddress = "db/wiseSaying/";
    private static final String lastIdFile = dirAddress + "lastId.txt";
    private static final String buildDataFile = dirAddress + "data.json";

    // 폴더 생성
    public static void createDir() {
        try {
            Files.createDirectories(Path.of(dirAddress));
        } catch (IOException e) {
            throw new RuntimeException("[" + dirAddress + " 폴더 생성 실패]", e);
        }
    }


    // lastId.txt
    // 파일 생성 및 Update
    public static void updateLastId(int id) {
        try {
            Files.writeString(Path.of(lastIdFile), String.valueOf(id));
        } catch (IOException e) {
            throw new RuntimeException("[" + lastIdFile + " 파일 생성 실패]", e);
        }
    }

    // 파일 조회
    // 마지막 id 값 반환
    public static int getLastId() {
        Path path = Path.of(lastIdFile);

        if (Files.notExists(path)) {
            return 0;
        }
        try {
            return Integer.parseInt(Files.readString(path).trim());
        }
        catch (IOException e) {
            throw new RuntimeException("[" + lastIdFile + " 파일 조회 실패]", e);
        }
    }

    // data.json 생성
    public static void buildDataJson(int maxId) {
        Path path = Path.of(buildDataFile);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[\n");

        for (int i = 1; i <= maxId; i++){
            Path filePath = Path.of(dirAddress + i + ".json");

            if (Files.notExists(filePath)) {
                continue;
            }
            try {
                // 양식 맞추기
                String content = "  " + Files.readString(filePath).replace("\n", "\n  ");
                stringBuilder.append(content);
                stringBuilder.append(",\n");
            }
            catch (IOException e){
                throw new RuntimeException("[" + dirAddress + i + ".json 파일 읽어오기 실패]", e);
            }
        }
        // 마지막 append가 항상 ",\n"이므로 lastIndexOf(",")는 반드시 구분자를 가리킴
        int lastComma = stringBuilder.lastIndexOf(",");
        if (lastComma != -1){ stringBuilder.deleteCharAt(lastComma); }
        stringBuilder.append("]");

        try {
            Files.writeString(path, stringBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException("[" + buildDataFile + " 파일 빌드 실패]", e);
        }
    }


    // {id}.json
    // 파일 생성 및 Update
    public static void createJsonFile(WiseSaying post) {
        int id = post.getId();
        try {
            Files.writeString(Path.of(dirAddress + id + ".json"), post.toJson());
        } catch (IOException e) {
            throw new RuntimeException("[" + dirAddress + id + ".json 파일 생성 실패]", e);
        }
    }
    // 파일 삭제
    public static void deleteJsonFile(int id) {
        try {
            Files.deleteIfExists(Path.of(dirAddress + id + ".json"));
        }
        catch (IOException e) {
            throw new RuntimeException("[" + dirAddress + id + ".json 파일 삭제 실패]", e);
        }
    }

    // 파일 조회 (전부 가져오기)
    public static HashMap<Integer, WiseSaying> loadPosts(int maxId){
        HashMap<Integer, WiseSaying> posts = new HashMap<>();
        for (int i = 1; i <= maxId; i++){
            Path filePath = Path.of(dirAddress + i + ".json");

            if (Files.notExists(filePath)) {
                continue;
            }
            try {
                String contents = Files.readString(filePath);
                WiseSaying post = parseJson(contents);
                posts.put(post.getId(), post);
            }
            catch (IOException e){
                throw new RuntimeException("[" + dirAddress + i + ".json 파일 읽어오기 실패]", e);
            }
        }

        return posts;
    }

    // Json 파싱 -> Post로 반환
    private static WiseSaying parseJson(String json) {
        int id = Integer.parseInt(extractValue(json, "id"));
        String author = extractValue(json, "author");
        String content = extractValue(json, "content");
        return new WiseSaying(id, author, content);
    }

    // Value 추출
    private static String extractValue(String json, String key){
        String startPattern = key.equals("id") ? "\"" + key + "\": " : "\"" + key + "\": \"";
        String endPattern = key.equals("id") ? "," : "\"";
        int valueStart = json.indexOf(startPattern) + startPattern.length();
        int valueEnd = json.indexOf(endPattern,  valueStart);
        return json.substring(valueStart, valueEnd);
    }

}
