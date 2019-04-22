package dst.courseproject.controllers;

import com.dropbox.core.DbxException;
import com.sun.security.auth.UserPrincipal;
import dst.courseproject.cloud.DropboxService;
import dst.courseproject.entities.Role;
import dst.courseproject.exceptions.FileTooLargeException;
import dst.courseproject.exceptions.WrongFileFormatException;
import dst.courseproject.models.binding.VideoAddBindingModel;
import dst.courseproject.models.binding.VideoEditBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.CategoryViewModel;
import dst.courseproject.models.view.CommentViewModel;
import dst.courseproject.models.view.UserViewModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.CategoryService;
import dst.courseproject.services.CommentService;
import dst.courseproject.services.UserService;
import dst.courseproject.services.VideoService;
import org.bytedeco.javacv.FrameGrabber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
@SuppressWarnings("unchecked")
public class VideoControllerTests {
    private VideoService videoService;
    private CategoryService categoryService;
    private CommentService commentService;
    private UserService userService;
    private DropboxService dropboxService;
    private VideoController controller;

    @Before
    public void init() throws DbxException {
        this.videoService = mock(VideoService.class);
        this.categoryService = mock(CategoryService.class);
        this.commentService = mock(CommentService.class);
        this.userService = mock(UserService.class);
        this.dropboxService = mock(DropboxService.class);
        this.controller = new VideoController(this.videoService, this.categoryService, this.commentService, this.userService, this.dropboxService, new ModelMapper());
        when(this.categoryService.getCategoriesNames()).thenReturn(new LinkedHashSet<>(){{
            add("Music");
            add("Sport");
        }});
        when(this.videoService.getVideoViewModel("aBcDe")).thenReturn(new VideoViewModel(){{
            setId("1");
            setTitle("Video");
            setVideoIdentifier("aBcDe");
            setAuthor(new UserViewModel(){{
                setEmail("gosho@gosho.com");
            }});
            setUsersLiked(new HashMap<>());
            setCategory(new CategoryViewModel(){{
                setName("Music");
            }});
        }});
        when(this.commentService.getCommentViewModelsByVideo("aBcDe")).thenReturn(new LinkedHashSet<>(){{
            add(new CommentViewModel(){{
                setContent("Long Content1");
            }});
            add(new CommentViewModel(){{
                setContent("Long Content2");
            }});
        }});
        when(this.dropboxService.getFileLink("aBcDe.mp4")).thenReturn("https://file-link.com");
        when(this.userService.getUserServiceModelByEmail("pesho@pesho.com")).thenReturn(new UserServiceModel(){{
            setId("1");
            setFirstName("Pesho");
            setLastName("Peshev");
            setAuthorities(new HashSet<>(){{
                add(new Role(){{
                    setAuthority("MODERATOR");
                }});
            }});
        }});
    }

    @Test
    public void videoController_addGetRequest_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        this.controller.add(modelAndView, model);
        List<String> expected = new ArrayList<>(){{
            add("Music");
            add("Sport");
        }};
        Set<String> actual = (Set<String>) modelAndView.getModelMap().get("categories");
        int i = 0;

        Assert.assertEquals("video-add", modelAndView.getViewName());
        Assert.assertEquals(VideoAddBindingModel.class, model.asMap().get("videoInput").getClass());
        Assert.assertEquals("Add Video", modelAndView.getModelMap().get("title"));
        Assert.assertEquals(expected.size(), actual.size());
        for (String act : actual) {
            Assert.assertEquals(expected.get(i++), act);
        }
    }

    @Test
    public void videoController_addPostMethodWithBindingError_returnCorrectModelAndView() throws FrameGrabber.Exception, IOException, DbxException {
        VideoAddBindingModel bindingModel = new VideoAddBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(bindingModel, "videoInput");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        bindingResult.addError(new ObjectError("err", "err"));
        this.controller.add(bindingModel, bindingResult, modelAndView, redirectAttributes, new UserPrincipal("pesho@pesho.com"));

        Assert.assertEquals(bindingResult, redirectAttributes.getFlashAttributes().get("org.springframework.validation.BindingResult.videoInput"));
        Assert.assertEquals(bindingModel, redirectAttributes.getFlashAttributes().get("videoInput"));
        Assert.assertEquals("redirect:add", modelAndView.getViewName());
    }

    @Test
    public void videoController_addPostMethodWithLargeFileException_returnCorrectModelAndView() throws FrameGrabber.Exception, IOException, DbxException, FileTooLargeException, WrongFileFormatException {
        VideoAddBindingModel bindingModel = new VideoAddBindingModel();
        Principal principal = new UserPrincipal("pesho@pesho.com");
        doThrow(new FileTooLargeException("")).when(this.videoService).addVideo(bindingModel, principal);
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(bindingModel, "videoInput");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.add(bindingModel, bindingResult, modelAndView, redirectAttributes, principal);

        Assert.assertEquals("error", redirectAttributes.getFlashAttributes().get("largeFile"));
        Assert.assertEquals("redirect:add", modelAndView.getViewName());
    }

    @Test
    public void videoController_addPostMethodWithCorrectData_returnCorrectModelAndView() throws FrameGrabber.Exception, IOException, DbxException {
        VideoAddBindingModel bindingModel = new VideoAddBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(bindingModel, "videoInput");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.add(bindingModel, bindingResult, modelAndView, redirectAttributes, new UserPrincipal("pesho@pesho.com"));

        Assert.assertEquals("redirect:../", modelAndView.getViewName());
    }

    @Test
    public void videoController_videoDetailsGetRequestWithNullPrincipal_returnCorrectModelAndView() throws DbxException {
        ModelAndView modelAndView = new ModelAndView();
        this.controller.videoDetails("aBcDe", modelAndView, null);
        VideoViewModel expectedVideo = new VideoViewModel(){{
            setId("1");
            setTitle("Video");
            setVideoIdentifier("aBcDe");
        }};
        VideoViewModel actualVideo = (VideoViewModel) modelAndView.getModelMap().get("video");
        List<CommentViewModel> expectedComments = new ArrayList<>(){{
            add(new CommentViewModel(){{
                setContent("Long Content1");
            }});
            add(new CommentViewModel(){{
                setContent("Long Content2");
            }});
        }};
        Set<CommentViewModel> actualComments = (Set<CommentViewModel>) modelAndView.getModelMap().get("comments");
        int i = 0;


        Assert.assertEquals("video-details", modelAndView.getViewName());
        Assert.assertEquals(Base64.getEncoder(), modelAndView.getModelMap().get("encoder"));
        Assert.assertEquals("https://file-link.com", modelAndView.getModelMap().get("videoFileUrl"));
        Assert.assertEquals(expectedVideo.getId(), actualVideo.getId());
        Assert.assertEquals(expectedVideo.getTitle(), actualVideo.getTitle());
        Assert.assertEquals(expectedVideo.getVideoIdentifier(), actualVideo.getVideoIdentifier());
        for (CommentViewModel actualComment : actualComments) {
            Assert.assertEquals(expectedComments.get(i++).getContent(), actualComment.getContent());
        }
    }

    @Test
    public void videoController_videoDetailsGetRequestWithActualPrincipal_returnCorrectModelAndView() throws DbxException {
        ModelAndView modelAndView = new ModelAndView();
        Principal principal = new UserPrincipal("pesho@pesho.com");
        this.controller.videoDetails("aBcDe", modelAndView, principal);
        VideoViewModel expectedVideo = new VideoViewModel(){{
            setId("1");
            setTitle("Video");
            setVideoIdentifier("aBcDe");
        }};
        VideoViewModel actualVideo = (VideoViewModel) modelAndView.getModelMap().get("video");
        List<CommentViewModel> expectedComments = new ArrayList<>(){{
            add(new CommentViewModel(){{
                setContent("Long Content1");
            }});
            add(new CommentViewModel(){{
                setContent("Long Content2");
            }});
        }};
        Set<CommentViewModel> actualComments = (Set<CommentViewModel>) modelAndView.getModelMap().get("comments");
        int i = 0;


        Assert.assertEquals("video-details", modelAndView.getViewName());
        Assert.assertEquals(Base64.getEncoder(), modelAndView.getModelMap().get("encoder"));
        Assert.assertEquals("https://file-link.com", modelAndView.getModelMap().get("videoFileUrl"));
        Assert.assertEquals(expectedVideo.getId(), actualVideo.getId());
        Assert.assertEquals(expectedVideo.getTitle(), actualVideo.getTitle());
        Assert.assertEquals(expectedVideo.getVideoIdentifier(), actualVideo.getVideoIdentifier());
        for (CommentViewModel actualComment : actualComments) {
            Assert.assertEquals(expectedComments.get(i++).getContent(), actualComment.getContent());
        }
        Assert.assertEquals("Pesho Peshev", modelAndView.getModelMap().get("principalName"));
        Assert.assertTrue((Boolean) modelAndView.getModelMap().get("isModerator"));
        Assert.assertFalse((Boolean) modelAndView.getModelMap().get("isLiked"));
    }

    @Test
    public void adminVideoController_editVideoGetRequestWithCorrectPrincipal_returnCorrectModelAndView() {
        when(this.categoryService.getCategoriesNames()).thenReturn(new LinkedHashSet<>(){{
            add("Music");
            add("Sport");
            add("Entertainment");
        }});
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        this.controller.editVideo("aBcDe", modelAndView, model, new UserPrincipal("gosho@gosho.com"));

        VideoEditBindingModel expectedVideo = new VideoEditBindingModel(){{
            setTitle("Video");
            setCategory("Music");
            setIdentifier("aBcDe");
        }};
        VideoEditBindingModel actualVideo = (VideoEditBindingModel) model.asMap().get("videoInput");
        List<String> expectedCategories = new ArrayList<>(){{
            add("Music");
            add("Sport");
            add("Entertainment");
        }};
        Set<String> actualCategories = (Set<String>) modelAndView.getModelMap().get("categories");
        int i = 0;

        Assert.assertEquals("video-edit", modelAndView.getViewName());
        Assert.assertEquals("Edit Video", modelAndView.getModelMap().get("title"));
        for (String actual : actualCategories) {
            Assert.assertEquals(expectedCategories.get(i++), actual);
        }
        Assert.assertEquals(expectedVideo.getTitle(), actualVideo.getTitle());
        Assert.assertEquals(expectedVideo.getCategory(), actualVideo.getCategory());
        Assert.assertEquals(expectedVideo.getIdentifier(), actualVideo.getIdentifier());
    }

    @Test
    public void adminVideoController_editVideoGetRequestWithIncorrectPrincipal_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        this.controller.editVideo("aBcDe", modelAndView, model, new UserPrincipal("pesho@pesho.com"));

        Assert.assertEquals("redirect:/", modelAndView.getViewName());
    }

    @Test
    public void adminVideoController_editVideoPostRequestWithError_returnCorrectModelAndView() {
        VideoEditBindingModel model = new VideoEditBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "videoInput");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        bindingResult.addError(new ObjectError("err", "err"));
        this.controller.editVideo("aBcDe", model, bindingResult, modelAndView, redirectAttributes);

        Assert.assertEquals(bindingResult, redirectAttributes.getFlashAttributes().get("org.springframework.validation.BindingResult.videoInput"));
        Assert.assertEquals(model, redirectAttributes.getFlashAttributes().get("videoInput"));
        Assert.assertEquals("redirect:aBcDe", modelAndView.getViewName());
    }

    @Test
    public void adminVideoController_editVideoPostRequestWithNoError_returnCorrectModelAndView() {
        VideoEditBindingModel model = new VideoEditBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "videoInput");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.editVideo("aBcDe", model, bindingResult, modelAndView, redirectAttributes);

        Assert.assertEquals("redirect:../aBcDe", modelAndView.getViewName());
    }

    @Test
    public void adminVideoController_deleteVideoGetRequestWithCorrectPrincipal_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        this.controller.deleteVideo("aBcDe", modelAndView, new UserPrincipal("gosho@gosho.com"));

        VideoViewModel expected = new VideoViewModel(){{
            setId("1");
            setTitle("Video");
            setVideoIdentifier("aBcDe");
            setCategory(new CategoryViewModel(){{
                setName("Music");
            }});
        }};
        VideoViewModel actual = (VideoViewModel) modelAndView.getModelMap().get("videoInput");

        Assert.assertEquals("video-delete", modelAndView.getViewName());
        Assert.assertEquals("Delete Video", modelAndView.getModelMap().get("title"));
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getVideoIdentifier(), actual.getVideoIdentifier());
        Assert.assertEquals(expected.getCategory().getName(), actual.getCategory().getName());
    }

    @Test
    public void adminVideoController_deleteVideoGetRequestWithIncorrectPrincipal_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        this.controller.deleteVideo("aBcDe", modelAndView, new UserPrincipal("pesho@pesho.com"));

        Assert.assertEquals("redirect:/", modelAndView.getViewName());
    }

    @Test
    public void adminVideoController_deleteVideoPostRequest_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        this.controller.deleteVideoConfirm("aBcDe", modelAndView);

        Assert.assertEquals("redirect:../../", modelAndView.getViewName());
    }
}
