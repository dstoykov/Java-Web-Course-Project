package dst.courseproject.models.view;

import dst.courseproject.entities.Category;
import dst.courseproject.entities.Comment;
import dst.courseproject.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class VideoViewModel {
    private String id;

    private String title;

    private String videoIdentifier;

    private String description;

    private UserViewModel author;

    private Category category;

    private Set<CommentViewModel> comments;

    private Long likes;

    private Boolean isLiked;

    private Long views;

    private LocalDateTime uploadedOn;

    public VideoViewModel() {
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

    public UserViewModel getAuthor() {
        return this.author;
    }

    public void setAuthor(UserViewModel author) {
        this.author = author;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<CommentViewModel> getComments() {
        return this.comments;
    }

    public void setComments(Set<CommentViewModel> comments) {
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

    public void setLiked(Boolean isLiked) {
        this.isLiked = isLiked;
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
