class Post {
    private String author;
    private String content;

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
