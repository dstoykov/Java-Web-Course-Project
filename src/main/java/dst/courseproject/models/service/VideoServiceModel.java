package dst.courseproject.models.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class VideoServiceModel {
    private String id;

    private String title;

    private String description;

    private String videoIdentifier;

    private UserServiceModel author;

    private CategoryServiceModel category;

    private Set<CommentServiceModel> comments;

    private Long views;

    private LocalDateTime uploadedOn;

    private Map<String, UserServiceModel> usersLiked;

    private String thumbnailUrl;

    private LocalDate deletedOn;

    public VideoServiceModel() {
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoIdentifier() {
        return this.videoIdentifier;
    }

    public void setVideoIdentifier(String videoIdentifier) {
        this.videoIdentifier = videoIdentifier;
    }

    public UserServiceModel getAuthor() {
        return this.author;
    }

    public void setAuthor(UserServiceModel author) {
        this.author = author;
    }

    public CategoryServiceModel getCategory() {
        return this.category;
    }

    public void setCategory(CategoryServiceModel category) {
        this.category = category;
    }

    public Set<CommentServiceModel> getComments() {
        return this.comments;
    }

    public void setComments(Set<CommentServiceModel> comments) {
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

    public Map<String, UserServiceModel> getUsersLiked() {
        return this.usersLiked;
    }

    public void setUsersLiked(Map<String, UserServiceModel> usersLiked) {
        this.usersLiked = usersLiked;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public LocalDate getDeletedOn() {
        return this.deletedOn;
    }

    public void setDeletedOn(LocalDate deletedOn) {
        this.deletedOn = deletedOn;
    }
}