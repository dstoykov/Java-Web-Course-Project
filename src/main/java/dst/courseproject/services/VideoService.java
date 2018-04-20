package dst.courseproject.services;

import dst.courseproject.entities.Video;
import dst.courseproject.models.binding.AddVideoBindingModel;
import dst.courseproject.models.view.VideoViewModel;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;

public interface VideoService {
    Set<VideoViewModel> mapVideoToModel(Set<Video> videoList);

    void addVideo(@Valid AddVideoBindingModel addVideoBindingModel, Principal principal) throws IOException;

    Long getTotalVideosCount();
}
