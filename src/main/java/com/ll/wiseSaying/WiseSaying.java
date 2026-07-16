package com.ll.wiseSaying;

class WiseSaying {
    private String author;
    private String content;
    private int id;

    // 생성자
    public WiseSaying(int id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    // getter
    public int getId() { return id; }
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

    // json 형식으로 변환
    public String toJson(){
        return "{\n" +
                "  \"id\": " +  id + ",\n" +
                "  \"content\": \"" +  content + "\",\n" +
                "  \"author\": \"" +  author + "\"\n" +
                "}";
    }
}
