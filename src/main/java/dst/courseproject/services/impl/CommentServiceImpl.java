package dst.courseproject.services.impl;

import dst.courseproject.entities.Comment;
import dst.courseproject.entities.User;
import dst.courseproject.entities.Video;
import dst.courseproject.models.binding.CommentAddBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.service.VideoServiceModel;
import dst.courseproject.repositories.CommentRepository;
import dst.courseproject.services.CommentService;
import dst.courseproject.services.UserService;
import dst.courseproject.services.VideoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final VideoService videoService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, VideoService videoService, UserService userService, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.videoService = videoService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(CommentAddBindingModel bindingModel, String videoIdentifier, String principalEmail) {
        Comment comment = this.modelMapper.map(bindingModel, Comment.class);
        VideoServiceModel videoServiceModel = this.videoService.getVideoServiceModelByIdentifier(videoIdentifier);
        UserServiceModel userServiceModel = this.userService.getUserServiceModelByEmail(principalEmail);

        comment.setVideo(this.modelMapper.map(videoServiceModel, Video.class));
        comment.setAuthor(this.modelMapper.map(userServiceModel, User.class));

        this.commentRepository.save(comment);
    }
}
