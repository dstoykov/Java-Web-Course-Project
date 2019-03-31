package dst.courseproject.models.view;

public class CommentViewModel {
    private String id;
    private String content;
    private String author;
    private String dateOfPublishing;

    public CommentViewModel() {
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

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateOfPublishing() {
        return this.dateOfPublishing;
    }

    public void setDateOfPublishing(String dateOfPublishing) {
        this.dateOfPublishing = dateOfPublishing;
    }
}
