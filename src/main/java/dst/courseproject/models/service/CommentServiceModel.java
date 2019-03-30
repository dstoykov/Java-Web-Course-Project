package dst.courseproject.models.service;

public class CommentServiceModel {
    private String id;
    private String content;
    private UserServiceModel author;

    public CommentServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserServiceModel getAuthor() {
        return this.author;
    }

    public void setAuthor(UserServiceModel author) {
        this.author = author;
    }
}
