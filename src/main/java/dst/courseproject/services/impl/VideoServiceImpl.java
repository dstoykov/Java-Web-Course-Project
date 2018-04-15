package dst.courseproject.services.impl;

import dst.courseproject.entities.Video;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.repositories.VideoRepository;
import dst.courseproject.services.VideoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, ModelMapper modelMapper) {
        this.videoRepository = videoRepository;
        this.modelMapper = modelMapper;
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
}
