package dst.courseproject.models.view;

import dst.courseproject.entities.User;
import dst.courseproject.entities.Video;

import java.time.LocalDateTime;

public class CommentViewModel {
    private String id;
    private String content;
    private Video video;
    private User author;
    private LocalDateTime dateOfPublishing;

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

    public Video getVideo() {
        return this.video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getDateOfPublishing() {
        return this.dateOfPublishing;
    }

    public void setDateOfPublishing(LocalDateTime dateOfPublishing) {
        this.dateOfPublishing = dateOfPublishing;
    }
}
