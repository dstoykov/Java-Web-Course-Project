package dst.courseproject.models.view;

import dst.courseproject.entities.Category;
import dst.courseproject.entities.Comment;
import dst.courseproject.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class VideoViewModel {
    private String id;

    private String title;

    private String videoIdentifier;

    private String description;

    private UserViewModel author;

    private CategoryViewModel category;

    private Set<CommentViewModel> comments;

    private Long views;

    private LocalDateTime uploadedOn;

    private Map<String, UserViewModel> usersLiked;

    private String thumbnailUrl;

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

    public CategoryViewModel getCategory() {
        return this.category;
    }

    public void setCategory(CategoryViewModel category) {
        this.category = category;
    }

    public Set<CommentViewModel> getComments() {
        return this.comments;
    }

    public void setComments(Set<CommentViewModel> comments) {
        this.comments = comments;
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

    public Map<String, UserViewModel> getUsersLiked() {
        return this.usersLiked;
    }

    public void setUsersLiked(Map<String, UserViewModel> usersLiked) {
        this.usersLiked = usersLiked;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}