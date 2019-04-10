package dst.courseproject.models.view;

import dst.courseproject.entities.Video;

import java.util.Set;

public class CategoryViewModel {
    private String id;

    private String name;

    private Set<Video> videos;

    public CategoryViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Video> getVideos() {
        return this.videos;
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }
}
