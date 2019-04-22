package dst.courseproject.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="video_id", nullable = false)
    private Video video;

    @ManyToOne
    @JoinColumn(name="author_id", nullable = false)
    private User author;

    @Column(name = "date_of_publishing", nullable = false)
    private LocalDateTime dateOfPublishing;

    @Column(name = "deleted_on")
    private LocalDate deletedOn;

    public Comment() {
        this.dateOfPublishing = LocalDateTime.now();
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

    public LocalDate getDeletedOn() {
        return this.deletedOn;
    }

    public void setDeletedOn(LocalDate deletedOn) {
        this.deletedOn = deletedOn;
    }
}
