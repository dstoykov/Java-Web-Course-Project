package dst.courseproject.services;

import dst.courseproject.entities.Comment;
import dst.courseproject.entities.User;
import dst.courseproject.entities.Video;
import dst.courseproject.models.binding.CommentAddBindingModel;
import dst.courseproject.models.service.CommentServiceModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.service.VideoServiceModel;
import dst.courseproject.models.view.CommentViewModel;
import dst.courseproject.repositories.CommentRepository;
import dst.courseproject.services.impl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentServiceTests {
    private CommentRepository commentRepository;
    private VideoService videoService;
    private UserService userService;
    private ModelMapper modelMapper;
    private CommentService commentService;

    @Before
    public void init() {
        this.commentRepository = mock(CommentRepository.class);
        this.videoService = mock(VideoService.class);
        this.userService = mock(UserService.class);
        this.modelMapper = new ModelMapper();
        this.commentService = new CommentServiceImpl(this.commentRepository, this.videoService, this.userService, this.modelMapper);
        when(this.commentRepository.getOne("1")).thenReturn(new Comment(){{
            setId("1");
            setContent("Comment");
            setVideo(new Video(){{
                setId("2");
            }});
            setAuthor(new User(){{
                setId("3");
            }});
        }});
    }

    @Test
    public void commentService_save_returnServiceModel() {
        when(this.userService.getUserServiceModelByEmail("pesho@pesho.com")).thenReturn(new UserServiceModel(){{
            setId("1");
            setEmail("pesho@pesho.com");
        }});
        when(this.videoService.getVideoServiceModelByIdentifier("aBcDe")).thenReturn(new VideoServiceModel(){{
            setId("1");
            setTitle("Video");
        }});
        //Arrange
        CommentAddBindingModel model = new CommentAddBindingModel(){{
            setContent("Long content");
        }};

        //Act
        CommentServiceModel actual = this.commentService.save(model, "aBcDe","pesho@pesho.com");
        CommentServiceModel expected = new CommentServiceModel(){{
            setContent("Long content");
            setAuthor(new UserServiceModel(){{
                setId("1");
                setEmail("pesho@pesho.com");
            }});
            setVideo(new VideoServiceModel(){{
                setId("1");
                setTitle("Video");
            }});
        }};

        //Arrange
        Assert.assertEquals(expected.getContent(), actual.getContent());
        Assert.assertEquals(expected.getAuthor().getEmail(), actual.getAuthor().getEmail());
        Assert.assertEquals(expected.getVideo().getTitle(), expected.getVideo().getTitle());
    }

    @Test
    public void commentService_getServiceModelById_returnCorrect() {
        //Arrange
        CommentServiceModel expected = new CommentServiceModel() {{
            setId("1");
            setContent("Comment");
            setAuthor(new UserServiceModel(){{
                setId("3");
            }});
        }};

        //Act
        CommentServiceModel actual = this.commentService.getCommentServiceModelById("1");

        //Assert
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getContent(), actual.getContent());
        Assert.assertEquals(expected.getAuthor().getId(), actual.getAuthor().getId());
    }

    @Test
    public void commentService_deleteComment_returnId() {
        //Act
        CommentServiceModel actual = this.commentService.remove("1");

        //Assert
        Assert.assertNotNull(actual.getDeletedOn());
    }
}
