package dst.courseproject.models.view;

import java.util.Set;

public class CategoryViewModel {
    private String id;

    private String name;

    private Set<VideoViewModel> videos;

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

    public Set<VideoViewModel> getVideos() {
        return this.videos;
    }

    public void setVideos(Set<VideoViewModel> videos) {
        this.videos = videos;
    }
}
