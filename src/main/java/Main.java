import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Board app = new Board(scanner);
        String input;

        System.out.println("== 명언 앱 ==");

        while (true) {
            // 입력
            System.out.print("명령) ");
            input = scanner.nextLine();

            switch (input.substring(0, 2)) {
                case "종료":
                    scanner.close();
                    return;
                case "등록":
                    app.createPost();
                    break;
                case "목록":
                    app.viewList();
                    break;
                case "삭제":
                    app.deletePost(Integer.parseInt(input.substring(6, input.length())));
                    break;
                case "수정":
                    app.updatePost(Integer.parseInt(input.substring(6, input.length())));
                    break;
            }

        }

    }
}

class Post {
    String author;
    String content;

    // 생성자
    public Post(String author, String content) {
        this.author = author;
        this.content = content;
    }

    // getter

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    // setter
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

class Board {
    HashMap<Integer, Post> postList;
    int id = 1;
    Scanner scanner;

    public Board(Scanner scanner) {
        this.postList = new HashMap<>();
        this.scanner = scanner;
    }

    // 등록
    public void createPost() {
        String author = "";
        String content = "";

        System.out.print("명언 : ");
        content = scanner.nextLine();

        System.out.print("작가 : ");
        author = scanner.nextLine();

        Post newPost = new Post(author, content);
        postList.put(id, newPost);

        System.out.println(id + "번 명언이 등록되었습니다.");
        id++;
    }

    // 목록 조회
    public void viewList() {

        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (int key : postList.keySet()) {
            System.out.println(key + " / " + postList.get(key).getAuthor() + " / " + postList.get(key).getContent());
        }
    }

    // 삭제
    public void deletePost(int delId) {
        if (postList.containsKey(delId)) {
            postList.remove(delId);
            System.out.println(delId + "번 명언이 삭제되었습니다.");
            return;
        }

        System.out.println(delId + "번 명언은 존재하지 않습니다.");
    }

    // 수정
    public void updatePost(int uId){
        if (postList.containsKey(uId)){
            String author = "";
            String content = "";

            System.out.println("명언(기존) : " + postList.get(uId).getContent());
            System.out.print("명언 : ");
            content = scanner.nextLine();

            System.out.println("명언(기존) : " + postList.get(uId).getAuthor());
            System.out.print("작가 : ");
            author = scanner.nextLine();

            postList.get(uId).setContent(content);
            postList.get(uId).setAuthor(author);
            return;
        }
        System.out.println(uId + "번 명언은 존재하지 않습니다.");
    }
}
