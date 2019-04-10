package dst.courseproject.services.impl;

import com.dropbox.core.DbxException;
import dst.courseproject.cloud.DropboxService;
import dst.courseproject.entities.Category;
import dst.courseproject.entities.User;
import dst.courseproject.entities.Video;
import dst.courseproject.exceptions.VideoAlreadyLiked;
import dst.courseproject.exceptions.VideoNotLiked;
import dst.courseproject.models.binding.VideoAddBindingModel;
import dst.courseproject.models.binding.VideoEditBindingModel;
import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.service.VideoServiceModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.repositories.VideoRepository;
import dst.courseproject.services.CategoryService;
import dst.courseproject.services.UserService;
import dst.courseproject.services.VideoService;
import dst.courseproject.thumbnail.ThumbnailExtractor;
import org.apache.commons.lang3.RandomStringUtils;
import org.bytedeco.javacv.FrameGrabber;
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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    private static final String MP4 = ".mp4";
    private static final String JPG = ".jpg";

    private final VideoRepository videoRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final DropboxService dropboxService;
    private final ThumbnailExtractor thumbnailExtractor;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, CategoryService categoryService, UserService userService, ModelMapper modelMapper, DropboxService dropboxService, ThumbnailExtractor thumbnailExtractor) {
        this.videoRepository = videoRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.dropboxService = dropboxService;
        this.thumbnailExtractor = thumbnailExtractor;
    }

    private File convert(MultipartFile multipartFile, String potentialFileName) throws IOException {
        File file = new File(potentialFileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();

        return file;
    }

    @Override
    public Long getTotalVideosCount() {
        return this.videoRepository.countAllByIdIsNotNull();
    }

    @Override
    public Set<VideoViewModel> getAllVideosAsViewModels() {
        Set<Video> videos = this.videoRepository.getAllByIdNotNullAndDeletedOnNull();
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
    public void addVideo(@Valid VideoAddBindingModel videoAddBindingModel, Principal principal) throws IOException, DbxException, FrameGrabber.Exception {
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

        File videoFile = this.convert(videoAddBindingModel.getVideoFile(), identifier);
        this.dropboxService.uploadVideo(videoFile, videoFile.getName());

        File imgFile = this.thumbnailExtractor.grab(identifier + MP4);
        this.dropboxService.uploadImage(imgFile, imgFile.getName());
        String imageUrl = this.dropboxService.getFileLink(imgFile.getName());

        video.setThumbnailUrl(imageUrl);

        this.videoRepository.save(video);
    }

    @Override
    public VideoViewModel getVideoViewModel(String identifier) {
        Video video = this.videoRepository.getByVideoIdentifierEqualsAndDeletedOnNull(identifier);
        return this.modelMapper.map(video, VideoViewModel.class);
    }

    @Override
    public void likeVideo(String videoIdentifier, String principalEmail) throws VideoAlreadyLiked {
        Video video = this.videoRepository.getByVideoIdentifierEqualsAndDeletedOnNull(videoIdentifier);
        User user = this.userService.getUserByEmail(principalEmail);
        if (video.getUsersLiked().containsKey(user.getId())) {
            throw new VideoAlreadyLiked("Video has been already liked by this user!");
        }

        video.getUsersLiked().put(user.getId(), user);

        this.videoRepository.save(video);
    }

    @Override
    public void unlikeVideo(String videoIdentifier, String principalEmail) throws VideoNotLiked {
        Video video = this.videoRepository.getByVideoIdentifierEqualsAndDeletedOnNull(videoIdentifier);
        User user = this.userService.getUserByEmail(principalEmail);
        if (!video.getUsersLiked().containsKey(user.getId())) {
            throw new VideoNotLiked("Video has not been liked by this user!");
        }

        video.getUsersLiked().remove(user.getId());

        this.videoRepository.save(video);
    }

    @Override
    public Integer getVideoLikes(String videoIdentifier) {
        Video video = this.videoRepository.getByVideoIdentifierEqualsAndDeletedOnNull(videoIdentifier);
        return video.getUsersLiked().size();
    }

    @Override
    public Set<VideoViewModel> getVideosByUserAsViewModels(UserServiceModel userServiceModel) {
        List<Video> videos = this.videoRepository.getAllByAuthorAndDeletedOnNullOrderByUploadedOnDesc(this.modelMapper.map(userServiceModel, User.class));
        Set<VideoViewModel> videoViewModels = new LinkedHashSet<>();
        for (Video video : videos) {
            videoViewModels.add(this.modelMapper.map(video, VideoViewModel.class));
        }

        return videoViewModels;
    }

    @Override
    public Set<VideoViewModel> getVideosByCategoryAsViewModel(CategoryServiceModel categoryServiceModel) {
        List<Video> videos = this.videoRepository.getAllByCategoryAndDeletedOnNullOrderByUploadedOnDesc(this.modelMapper.map(categoryServiceModel, Category.class));
        Set<VideoViewModel> videoViewModels = new LinkedHashSet<>();
        for (Video video : videos) {
            videoViewModels.add(this.modelMapper.map(video, VideoViewModel.class));
        }

        return videoViewModels;
    }

    @Override
    public Set<VideoViewModel> getLastTenVideosByUserAsViewModelsExceptCurrent(UserServiceModel author, String videoIdentifier) {
        List<Video> videos = this.videoRepository.getAllByAuthorAndDeletedOnNullOrderByViewsDesc(this.modelMapper.map(author, User.class));
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
        Video video = this.videoRepository.getByVideoIdentifierEqualsAndDeletedOnNull(identifier);
        return this.modelMapper.map(video, VideoServiceModel.class);
    }

    @Override
    public void increaseVideoViewsByOne(String identifier) {
        Video video = this.videoRepository.getByVideoIdentifierEqualsAndDeletedOnNull(identifier);
        video.setViews(video.getViews() + 1);
        this.videoRepository.save(video);
    }

    @Override
    public void editVideoData(@Valid VideoEditBindingModel videoEditBindingModel, String identifier) {
        Video video = this.videoRepository.getByVideoIdentifierEqualsAndDeletedOnNull(identifier);
        CategoryServiceModel categoryServiceModel = this.categoryService.getCategoryServiceModelByName(videoEditBindingModel.getCategory());

        video.setTitle(videoEditBindingModel.getTitle());
        video.setDescription(videoEditBindingModel.getDescription());
        video.setCategory(this.modelMapper.map(categoryServiceModel, Category.class));

        this.videoRepository.save(video);
    }

    @Override
    public void deleteVideo(String identifier) {
        Video video = this.videoRepository.getByVideoIdentifierEqualsAndDeletedOnNull(identifier);
        video.setDeletedOn(LocalDate.now());

        this.videoRepository.save(video);
    }

    @Override
    public Set<VideoViewModel> getViewModelsForSearch(String query) {
        Set<Video> videos = this.videoRepository.getAllByDeletedOnNullOrderByViewsDesc();
        Set<VideoViewModel> videoViewModels = new LinkedHashSet<>();
        for (Video video : videos) {
            if (video.getTitle().toLowerCase().contains(query.toLowerCase())) {
                videoViewModels.add(this.modelMapper.map(video, VideoViewModel.class));
            }
        }

        return videoViewModels;
    }
}