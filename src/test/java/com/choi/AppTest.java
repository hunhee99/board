package com.choi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    @BeforeEach
    void beforeEach() {
        TestUtil.dbFolderClear();
    }

    @Test
    @DisplayName("'== 명언 앱 ==' 출력")
    void t1(){
        String out = AppTestRunner.run("종료");

        assertThat(out).contains("== 명언 앱 ==");
    }

    @Test
    @DisplayName("등록")
    void t2(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);
        assertThat(out).contains("명령) ");
        assertThat(out).contains("명언 : ");
        assertThat(out).contains("작가 : ");

    }

    @Test
    @DisplayName("등록시 생선된 명언번호 노출")
    void t3(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);

        assertThat(out).contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("등록할때 마다 생성되는 명언번호가 증가")
    void t4(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);

        assertThat(out).contains("1번 명언이 등록되었습니다.");
        assertThat(out).contains("2번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("목록")
    void t5(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                종료
                """);
        assertThat(out).contains("""
                번호 / 작가 / 명언
                ----------------------
                2 / 작자미상 / 과거에 집착하지 마라.
                1 / 작자미상 / 현재를 사랑하라.
                """);
    }

    @Test
    @DisplayName("명언삭제")
    void t6(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                종료
                """);

        assertThat(out).contains("1번 명언이 삭제되었습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 명언삭제에 대한 예외처리")
    void t7(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                종료
                """);

        assertThat(out).contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("명언수정")
    void t8(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                수정?id=3
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                목록
                종료
                """);

        assertThat(out).contains("3번 명언은 존재하지 않습니다.");
        assertThat(out).contains("""
                번호 / 작가 / 명언
                ----------------------
                2 / 홍길동 / 현재와 자신을 사랑하라.
                """);
    }

    @Test
    @DisplayName("파일을 통한 영속성")
    void t9(){
        String out1 = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                종료
                """);
        String out2 = AppTestRunner.run("""
                목록
                종료
                """);

        assertThat(out2).contains("""
                번호 / 작가 / 명언
                ----------------------
                2 / 작자미상 / 과거에 집착하지 마라.
                1 / 작자미상 / 현재를 사랑하라.
                """);

    }

    @Test
    @DisplayName("data.json 빌드")
    void t10() throws IOException {
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                목록
                빌드
                종료
                """);

        assertThat(out).contains("data.json 파일의 내용이 갱신되었습니다.");


        assertThat(TestUtil.readDataJson()).isEqualTo("""
                [
                  {
                    "id": 2,
                    "content": "현재와 자신을 사랑하라.",
                    "author": "홍길동"
                  }
                """ + "]");

    }


    @Test
    @DisplayName("검색")
    void t11(){
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=content&keyword=과거
                목록?keywordType=author&keyword=작자
                종료
                """);

        assertThat(out).contains("""
                ----------------------
                검색타입 : content
                검색어 : 과거
                ----------------------
                번호 / 작가 / 명언
                ----------------------
                2 / 작자미상 / 과거에 집착하지 마라.
                """);

        assertThat(out).contains("""
                ----------------------
                검색타입 : author
                검색어 : 작자
                ----------------------
                번호 / 작가 / 명언
                ----------------------
                2 / 작자미상 / 과거에 집착하지 마라.
                1 / 작자미상 / 현재를 사랑하라.
                """);
    }

    @Test
    @DisplayName("페이징")
    void t12(){
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            sb.append("등록\n명언 " + i + "\n작자미상 " + i + "\n");
        }
        sb.append("""
                목록
                목록?page=2
                종료
                """);


        String out = AppTestRunner.run(sb.toString());

        assertThat(out).contains("""
                번호 / 작가 / 명언
                ----------------------
                10 / 작자미상 10 / 명언 10
                9 / 작자미상 9 / 명언 9
                8 / 작자미상 8 / 명언 8
                7 / 작자미상 7 / 명언 7
                6 / 작자미상 6 / 명언 6
                ----------------------
                페이지 : [1] / 2
                """);

        assertThat(out).contains("""
                번호 / 작가 / 명언
                ----------------------
                5 / 작자미상 5 / 명언 5
                4 / 작자미상 4 / 명언 4
                3 / 작자미상 3 / 명언 3
                2 / 작자미상 2 / 명언 2
                1 / 작자미상 1 / 명언 1
                ----------------------
                페이지 : 1 / [2]
                """);

    }
}
