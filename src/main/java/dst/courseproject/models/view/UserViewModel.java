package dst.courseproject.models.view;

import dst.courseproject.entities.Comment;
import dst.courseproject.entities.Role;
import dst.courseproject.entities.Video;

import java.time.LocalDate;
import java.util.Set;

public class UserViewModel {
    private String id;

    private String email;

    private String firstName;

    private String lastName;

    private Set<Video> videos;

    private Set<Comment> comments;

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

    public Set<Video> getVideos() {
        return this.videos;
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
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

    public boolean hasRole(String roleName) {
        for (Role role1 : this.getAuthorities()) {
            if (role1.getAuthority().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
