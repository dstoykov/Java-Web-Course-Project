package dst.courseproject.services.impl;

import com.dropbox.core.DbxException;
import dst.courseproject.cloud.DropboxService;
import dst.courseproject.entities.Category;
import dst.courseproject.entities.User;
import dst.courseproject.entities.Video;
import dst.courseproject.models.binding.VideoAddBindingModel;
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

        File file = this.convert(videoAddBindingModel.getVideoFile(), identifier);
        this.dropboxService.uploadFile(file, file.getName());

        this.videoRepository.save(video);
    }

    @Override
    public VideoViewModel getVideoViewModelForDetailsById(String id) {
        Video video = this.increaseVideoViewsByOneAndSave(id);
        VideoViewModel videoViewModel = this.modelMapper.map(video, VideoViewModel.class);

        return videoViewModel;
    }

    private Video increaseVideoViewsByOneAndSave(String id) {
        Video video = this.videoRepository.getOne(id);
        video.setViews(video.getViews() + 1);
        this.videoRepository.save(video);

        return video;
    }

    private File convert(MultipartFile multipartFile, String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();

        return file;
    }
}
