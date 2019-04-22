package dst.courseproject.models.view;

import dst.courseproject.entities.Role;

import java.time.LocalDate;
import java.util.Set;

public class UserViewModel {
    private String id;

    private String email;

    private String firstName;

    private String lastName;

    private Set<VideoViewModel> videos;

    private Set<CommentViewModel> comments;

    private LocalDate deletedOn;

    private Set<Role> authorities;

    public UserViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<VideoViewModel> getVideos() {
        return this.videos;
    }

    public void setVideos(Set<VideoViewModel> videos) {
        this.videos = videos;
    }

    public Set<CommentViewModel> getComments() {
        return this.comments;
    }

    public void setComments(Set<CommentViewModel> comments) {
        this.comments = comments;
    }

    public LocalDate getDeletedOn() {
        return this.deletedOn;
    }

    public void setDeletedOn(LocalDate deletedOn) {
        this.deletedOn = deletedOn;
    }

    public Set<Role> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }
}
