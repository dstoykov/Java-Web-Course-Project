package dst.courseproject.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, unique = true)
    private String videoIdentifier;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(nullable = false, name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(nullable = false, name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "video")
    private Set<Comment> comments;

    @Column(nullable = false, columnDefinition = "BIGINT default 0")
    private Long likes;

    @Column(nullable = false)
    private Boolean isLiked;

    @Column(nullable = false, columnDefinition = "BIGINT default 0")
    private Long views;

    @Column(nullable = false)
    private LocalDateTime uploadedOn;

    public Video() {
        this.comments = new HashSet<>();
        this.likes = 0L;
        this.isLiked = false;
        this.views = 0L;
        this.uploadedOn = LocalDateTime.now();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoIdentifier() {
        return this.videoIdentifier;
    }

    public void setVideoIdentifier(String videoIdentifier) {
        this.videoIdentifier = videoIdentifier;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Long getLikes() {
        return this.likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Boolean getLiked() {
        return this.isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    public Long getViews() {
        return this.views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public LocalDateTime getUploadedOn() {
        return this.uploadedOn;
    }

    public void setUploadedOn(LocalDateTime uploadedOn) {
        this.uploadedOn = uploadedOn;
    }
}
