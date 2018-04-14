package dst.courseproject.services;

import dst.courseproject.entities.Video;
import dst.courseproject.models.view.VideoViewModel;

import java.util.List;
import java.util.Set;

public interface VideoService {
    Set<VideoViewModel> mapVideoToModel(Set<Video> videoList);
}
