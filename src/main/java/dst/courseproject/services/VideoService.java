package dst.courseproject.services;

import com.dropbox.core.DbxException;
import dst.courseproject.entities.User;
import dst.courseproject.entities.Video;
import dst.courseproject.models.binding.VideoAddBindingModel;
import dst.courseproject.models.service.VideoServiceModel;
import dst.courseproject.models.view.VideoViewModel;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;

public interface VideoService {
    Set<VideoViewModel> getAllVideosAsViewModels();

    Set<VideoViewModel> mapVideoToModel(Set<Video> videoList);

    void addVideo(@Valid VideoAddBindingModel videoAddBindingModel, Principal principal) throws IOException, DbxException;

    Long getTotalVideosCount();

    VideoViewModel getVideoViewModelForDetailsByIdentifier(String identifier);

    void likeVideo(String identifier);

    void dislikeVideo(String identifier);

    Set<VideoViewModel> getLastTenVideosByUserAsViewModelsExceptCurrent(User author, String videoIdentifier);

    VideoServiceModel getVideoServiceModelByIdentifier(String identifier);
}
