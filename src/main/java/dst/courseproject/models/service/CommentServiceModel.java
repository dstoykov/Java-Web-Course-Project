package dst.courseproject.models.service;

import java.time.LocalDate;

public class CommentServiceModel {
    private String id;
    private String content;
    private UserServiceModel author;
    private VideoServiceModel video;
    private LocalDate deletedOn;

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

    public VideoServiceModel getVideo() {
        return this.video;
    }

    public void setVideo(VideoServiceModel video) {
        this.video = video;
    }

    public LocalDate getDeletedOn() {
        return this.deletedOn;
    }

    public void setDeletedOn(LocalDate deletedOn) {
        this.deletedOn = deletedOn;
    }
}
