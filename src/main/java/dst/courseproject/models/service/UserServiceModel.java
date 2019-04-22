package dst.courseproject.models.service;

import dst.courseproject.entities.Role;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public class UserServiceModel {
    private String id;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private Set<VideoServiceModel> videos;

    private Set<Role> authorities;

    private Map<String, VideoServiceModel> likedVideos;

    private LocalDate deletedOn;

    public UserServiceModel() {
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<VideoServiceModel> getVideos() {
        return this.videos;
    }

    public void setVideos(Set<VideoServiceModel> videos) {
        this.videos = videos;
    }

    public Set<Role> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public Map<String, VideoServiceModel> getLikedVideos() {
        return this.likedVideos;
    }

    public void setLikedVideos(Map<String, VideoServiceModel> likedVideos) {
        this.likedVideos = likedVideos;
    }

    public LocalDate getDeletedOn() {
        return this.deletedOn;
    }

    public void setDeletedOn(LocalDate deletedOn) {
        this.deletedOn = deletedOn;
    }
}
