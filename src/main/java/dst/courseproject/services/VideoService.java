package dst.courseproject.services;

import com.dropbox.core.DbxException;
import dst.courseproject.entities.Video;
import dst.courseproject.exceptions.FileTooLargeException;
import dst.courseproject.exceptions.VideoAlreadyLiked;
import dst.courseproject.exceptions.VideoNotLiked;
import dst.courseproject.models.binding.VideoAddBindingModel;
import dst.courseproject.models.binding.VideoEditBindingModel;
import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.service.VideoServiceModel;
import dst.courseproject.models.view.VideoViewModel;
import org.bytedeco.javacv.FrameGrabber;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Set;

public interface VideoService {
    Set<VideoViewModel> getAllVideosAsViewModels();

    Set<VideoViewModel> mapVideoToModel(Set<Video> videoList);

    void addVideo(@Valid VideoAddBindingModel videoAddBindingModel, Principal principal) throws IOException, DbxException, FrameGrabber.Exception, FileTooLargeException;

    Long getTotalActiveVideosCount();

    VideoViewModel getVideoViewModel(String identifier);

    void likeVideo(String videoIdentifier, String principalEmail) throws VideoAlreadyLiked;

    void unlikeVideo(String videoIdentifier, String principalEmail) throws VideoNotLiked;

    Integer getVideoLikes(String videoIdentifier);

    Set<VideoViewModel> getVideosByUserAsViewModels(UserServiceModel userServiceModel);

    Set<VideoViewModel> getVideosByCategoryAsViewModel(CategoryServiceModel categoryServiceModel);

    Set<VideoViewModel> getLastTenVideosByUserAsViewModelsExceptCurrent(UserServiceModel author, String videoIdentifier);

    VideoServiceModel getVideoServiceModelByIdentifier(String identifier);

    void increaseVideoViewsByOne(String identifier);

    void editVideoData(@Valid VideoEditBindingModel videoEditBindingModel, String identifier);

    void deleteVideo(String identifier);

    Set<VideoViewModel> getViewModelsForSearch(String query);
}
