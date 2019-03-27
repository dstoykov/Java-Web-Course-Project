package dst.courseproject.entities;

import org.hibernate.annotations.GenericGenerator;

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

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name="video_id")
    private Video video;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    @Column(nullable = false)
    private LocalDateTime dateOfPublishing;

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
}
