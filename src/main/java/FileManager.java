import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

class FileManager {
    private static final String dirAddress = "db/wiseSaying/";
    private static final String lastIdFile = dirAddress + "lastId.txt";


    // 폴더 생성
    public static void createDir() {
        try {
            Files.createDirectories(Path.of(dirAddress));
        } catch (IOException e) {
            System.out.println("[" + dirAddress + " 폴더 생성 실패]: " + e.getMessage());
        }
    }


    // lastId.txt
    // 파일 생성 및 Update
    public static void updateLastId(int id) {
        try {
            Files.writeString(Path.of(lastIdFile), String.valueOf(id));
        } catch (IOException e) {
            System.out.println("[" + lastIdFile + " 파일 생성 실패]: " + e.getMessage());
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
            System.out.println("[" + lastIdFile + " 파일 조회 실패]: " + e.getMessage());
            return 0;
        }
    }


    // {id}.json
    // 파일 생성 및 Update
    public static void createJsonFile(int id, String json) {
        try {
            Files.writeString(Path.of(dirAddress + id + ".json"), json);
        } catch (IOException e) {
            System.out.println("[" + dirAddress + id + ".json 파일 생성 실패]: " + e.getMessage());
        }
    }
    // 파일 삭제
    public static void deleteJsonFile(int id) {
        try {
            Files.deleteIfExists(Path.of(dirAddress + id + ".json"));
        }
        catch (IOException e) {
            System.out.println("[" + dirAddress + id + ".json 파일 삭제 실패]: " + e.getMessage());
        }
    }

    // 파일 조회 (전부 가져오기)
    public static HashMap<Integer, Post> loadPosts(int maxId){
        HashMap<Integer, Post> posts = new HashMap<>();
        for (int i = 1; i <= maxId; i++){
            Path filePath = Path.of(dirAddress + i + ".json");

            if (Files.notExists(filePath)) {
                continue;
            }
            try {
                String contents = Files.readString(filePath);
                posts.put(i, parseJson(contents));
            }
            catch (IOException e){
                System.out.println("[" + dirAddress + i + ".json 파일 읽어오기 실패]: " + e.getMessage());
                continue;
            }
        }

        return posts;
    }

    // Json 파싱 -> Post로 반환
    private static Post parseJson(String json) {
        String author = extractValue(json, "author");
        String content = extractValue(json, "content");
        return new Post(author, content);
    }

    // Value 추출
    private static String extractValue(String json, String key){
        String pattern = "\"" + key + "\": \"";
        int valueStart = json.indexOf(pattern) + pattern.length();
        int valueEnd = json.indexOf("\"",  valueStart);

        return json.substring(valueStart, valueEnd);
    }



}
