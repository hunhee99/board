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


