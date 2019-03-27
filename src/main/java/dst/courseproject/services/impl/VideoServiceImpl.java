package dst.courseproject.services.impl;

import com.dropbox.core.DbxException;
import dst.courseproject.cloud.DropboxService;
import dst.courseproject.entities.Category;
import dst.courseproject.entities.User;
import dst.courseproject.entities.Video;
import dst.courseproject.models.binding.VideoAddBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.service.VideoServiceModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.repositories.VideoRepository;
import dst.courseproject.services.CategoryService;
import dst.courseproject.services.UserService;
import dst.courseproject.services.VideoService;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final DropboxService dropboxService;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, CategoryService categoryService, UserService userService, ModelMapper modelMapper, DropboxService dropboxService) {
        this.videoRepository = videoRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.dropboxService = dropboxService;
    }

    @Override
    public Long getTotalVideosCount() {
        return this.videoRepository.countAllByIdIsNotNull();
    }

    @Override
    public Set<VideoViewModel> getAllVideosAsViewModels() {
        Set<Video> videos = this.videoRepository.getAllByIdNotNull();
        Set<VideoViewModel> videoViewModels = new HashSet<>();
        for (Video video : videos) {
            videoViewModels.add(this.modelMapper.map(video, VideoViewModel.class));
        }

        return videoViewModels;
    }

    @Override
    public Set<VideoViewModel> mapVideoToModel(Set<Video> videoList) {
        Set<VideoViewModel> videoViewModels = new HashSet<>();
        for (Video video : videoList) {
            VideoViewModel videoViewModel = this.modelMapper.map(video, VideoViewModel.class);
            videoViewModels.add(videoViewModel);
        }

        return videoViewModels;
    }

    @Override
    public void addVideo(@Valid VideoAddBindingModel videoAddBindingModel, Principal principal) throws IOException, DbxException {
        String identifier = RandomStringUtils.randomAlphanumeric(11);

        Video video = new Video();
        Category category = this.categoryService.findByName(videoAddBindingModel.getCategory());
        User user = this.userService.getUserByEmail(principal.getName());

        video.setTitle(videoAddBindingModel.getTitle());
        video.setDescription(videoAddBindingModel.getDescription());
        video.setVideoIdentifier(identifier);
        video.setAuthor(user);
        video.setCategory(category);

        System.out.println(videoAddBindingModel.getVideoFile().getSize());

        File file = this.convert(videoAddBindingModel.getVideoFile(), identifier);
        this.dropboxService.uploadFile(file, file.getName());

        this.videoRepository.save(video);
    }

    @Override
    public VideoViewModel getVideoViewModelForDetailsByIdentifier(String identifier) {
        Video video = this.increaseVideoViewsByOneAndSave(identifier);
        return this.modelMapper.map(video, VideoViewModel.class);
    }

    @Override
    public void likeVideo(String identifier) {
        Video video = this.videoRepository.getByVideoIdentifierEquals(identifier);
        if (!video.getLiked()) {
            video.setLikes(video.getLikes() + 1);
            video.setLiked(true);
            this.videoRepository.save(video);
        }
    }

    @Override
    public void dislikeVideo(String identifier) {
        Video video = this.videoRepository.getByVideoIdentifierEquals(identifier);
        if (video.getLiked()) {
            video.setLikes(video.getLikes() - 1);
            video.setLiked(false);
            this.videoRepository.save(video);
        }
    }

    @Override
    public Set<VideoViewModel> getLastTenVideosByUserAsViewModelsExceptCurrent(UserServiceModel author, String videoIdentifier) {
        List<Video> videos = this.videoRepository.getAllByAuthorOrderByViewsDesc(this.modelMapper.map(author, User.class));
        Set<VideoViewModel> videoViewModels = new LinkedHashSet<>();
        int limit = 10;
        for (int i = 0; i < videos.size(); i++) {
            Video current = videos.get(i);
            if (current.getVideoIdentifier().equals(videoIdentifier)) {
                limit++;
                continue;
            }
            if (i == limit) {
                break;
            }
            videoViewModels.add(this.modelMapper.map(current, VideoViewModel.class));
        }

        return videoViewModels;
    }

    @Override
    public VideoServiceModel getVideoServiceModelByIdentifier(String identifier) {
        Video video = this.videoRepository.getByVideoIdentifierEquals(identifier);
        return this.modelMapper.map(video, VideoServiceModel.class);
    }

    private Video increaseVideoViewsByOneAndSave(String identifier) {
        Video video = this.videoRepository.getByVideoIdentifierEquals(identifier);
        video.setViews(video.getViews() + 1);
        this.videoRepository.save(video);

        return video;
    }

    private File convert(MultipartFile multipartFile, String potentialFileName) throws IOException {
        File file = new File(potentialFileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();

        return file;
    }
}
