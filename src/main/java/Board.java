import java.util.HashMap;
import java.util.Scanner;

class Board {
    HashMap<Integer, Post> postList;
    int id = 0;
    Scanner scanner;

    public Board(Scanner scanner) {
        FileManager.createDir();
        this.scanner = scanner;
        id = FileManager.getLastId();
        postList = FileManager.loadPosts(id);
    }

    // 등록
    public void createPost() {

        System.out.print("명언 : ");
        String content = scanner.nextLine();

        System.out.print("작가 : ");
        String author = scanner.nextLine();

        Post newPost = new Post(author, content);

        postList.put(++id, newPost);

        // 파일로 저장
        FileManager.updateLastId(id);
        FileManager.createJsonFile(id, toJson(id));

        System.out.println(id + "번 명언이 등록되었습니다.");
    }

    // 목록 조회
    public void viewList() {

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (int i = id; i > 0; i--) {
            if (postList.containsKey(i)) {
                System.out.println(i + " / " + postList.get(i).getAuthor() + " / " + postList.get(i).getContent());
            }
        }
    }

    // 삭제
    public void deletePost(int deleteId) {
        if (postList.containsKey(deleteId)) {
            postList.remove(deleteId);

            // 파일 삭제
            FileManager.deleteJsonFile(deleteId);

            System.out.println(deleteId + "번 명언이 삭제되었습니다.");
            return;
        }

        System.out.println(deleteId + "번 명언은 존재하지 않습니다.");
    }

    // 수정
    public void updatePost(int updateId) {
        if (postList.containsKey(updateId)) {
            String author = "";
            String content = "";

            System.out.println("명언(기존) : " + postList.get(updateId).getContent());
            System.out.print("명언 : ");
            content = scanner.nextLine();

            System.out.println("명언(기존) : " + postList.get(updateId).getAuthor());
            System.out.print("작가 : ");
            author = scanner.nextLine();

            postList.get(updateId).setContent(content);
            postList.get(updateId).setAuthor(author);

            // 파일 업데이트
            FileManager.createJsonFile(updateId, toJson(updateId));

            return;
        }
        System.out.println(updateId + "번 명언은 존재하지 않습니다.");
    }

    // json 형식으로 변환
    private String toJson(int id){
        String content  = postList.get(id).getContent();
        String author = postList.get(id).getAuthor();
        return "{\n" +
                "  \"id\": " +  id + ",\n" +
                "  \"content\": \"" +  content + "\",\n" +
                "  \"author\": \"" +  author + "\"\n" +
                "}";
    }
}
