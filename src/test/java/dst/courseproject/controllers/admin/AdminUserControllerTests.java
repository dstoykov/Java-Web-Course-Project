package dst.courseproject.controllers.admin;

import com.sun.security.auth.UserPrincipal;
import dst.courseproject.entities.Role;
import dst.courseproject.exceptions.PasswordsMismatchException;
import dst.courseproject.exceptions.UserIsModeratorAlreadyException;
import dst.courseproject.exceptions.UserIsNotModeratorException;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.UserService;
import dst.courseproject.services.VideoService;
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

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
@SuppressWarnings("unchecked")
public class AdminUserControllerTests {
    private AdminUserController controller;
    private UserService userService;
    private VideoService videoService;

    @Before
    public void init() {
        this.userService = mock(UserService.class);
        this.videoService = mock(VideoService.class);
        this.controller = new AdminUserController(this.userService, this.videoService, new ModelMapper());
        when(this.userService.getUserServiceModelById("1")).thenReturn(new UserServiceModel(){{
            setId("1");
            setEmail("pesho@pesho.com");
            setFirstName("Pesho");
        }});
        when(this.userService.getUserViewModelById("1")).thenReturn(new UserViewModel(){{
            setId("1");
            setEmail("pesho@peshev.com");
            setFirstName("Pesho");
            setAuthorities(new HashSet<>(){{
                add(new Role(){{
                    setAuthority("USER");
                }});
                add(new Role(){{
                    setAuthority("MODERATOR");
                }});
            }});
        }});
    }

    @Test
    public void adminUserController_allUsersGetRequest_returnCorrectModelAndView(){
        when(this.userService.getListWithViewModels("admin@admin.com")).thenReturn(new LinkedList<>(){{
            add(new UserViewModel(){{
                setId("1");
                setEmail("gosho@goshomail.com");
            }});
            add(new UserViewModel(){{
                setId("2");
                setEmail("pesho@peshomail.com");
            }});
        }});
        ModelAndView modelAndView = new ModelAndView();
        Principal principal = new UserPrincipal("admin@admin.com");
        List<UserViewModel> expected = new ArrayList<>(){{
            add(new UserViewModel(){{
                setId("1");
                setEmail("gosho@goshomail.com");
            }});
            add(new UserViewModel(){{
                setId("2");
                setEmail("pesho@peshomail.com");
            }});
        }};
        this.controller.allUsers(modelAndView, principal);
        List<UserViewModel> actual = (List<UserViewModel>) modelAndView.getModelMap().get("userModels");

        Assert.assertEquals("All Users", modelAndView.getModelMap().get("title"));
        Assert.assertEquals("admin-users-all", modelAndView.getViewName());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i).getId(), actual.get(i).getId());
            Assert.assertEquals(expected.get(i).getEmail(), actual.get(i).getEmail());
        }

    }

    @Test
    public void adminUserController_userProfileGetRequest_returnCorrectModelAndView() {
        when(this.videoService.getVideosByUserAsViewModels(null)).thenReturn(new HashSet<>(){{
            add(new VideoViewModel(){{
                setTitle("Video1");
            }});
            add(new VideoViewModel(){{
                setTitle("Video2");
            }});
        }});
        UserViewModel expectedUser = new UserViewModel(){{
            setId("1");
            setEmail("pesho@peshev.com");
            setFirstName("Pesho");
            setAuthorities(new HashSet<>(){{
                add(new Role(){{
                    setAuthority("USER");
                }});
                add(new Role(){{
                    setAuthority("MODERATOR");
                }});
            }});
        }};
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        this.controller.userProfile("1", modelAndView, model);
        UserViewModel actualUser = (UserViewModel) modelAndView.getModelMap().get("user");
        List<VideoViewModel> expectedVideos = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setTitle("Video1");
            }});
            add(new VideoViewModel(){{
                setTitle("Video2");
            }});
        }};
        int i = 0;
        Set<VideoViewModel> actualVideos = (Set<VideoViewModel>) modelAndView.getModelMap().get("videos");

        Assert.assertEquals("admin-user-profile", modelAndView.getViewName());
        Assert.assertEquals("Pesho's Profile", modelAndView.getModelMap().get("title"));
        Assert.assertFalse((Boolean) modelAndView.getModelMap().get("isAdminUser"));
        Assert.assertTrue((Boolean) modelAndView.getModelMap().get("isModeratorUser"));
        Assert.assertEquals(expectedUser.getId(), actualUser.getId());
        Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assert.assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        for (VideoViewModel actualVideo : actualVideos) {
            Assert.assertEquals(expectedVideos.get(i++).getTitle(), actualVideo.getTitle());
        }
    }

    @Test
    public void adminUserController_editProfileGetRequest_returnCorrectModelAndView() {
        when(this.userService.getUserServiceModelById("1")).thenReturn(new UserServiceModel(){{
            setId("1");
            setEmail("pesho@pesho.com");
            setFirstName("Pesho");
        }});
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        this.controller.editProfile("1", modelAndView, model, new ModelMapper());
        UserEditBindingModel expected = new UserEditBindingModel(){{
            setId("1");
            setFirstName("Pesho");
        }};
        UserEditBindingModel actual = (UserEditBindingModel) model.asMap().get("userInput");

        Assert.assertEquals("admin-user-edit", modelAndView.getViewName());
        Assert.assertEquals("Edit Profile", modelAndView.getModelMap().get("title"));
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
    }

    @Test
    public void adminUserController_editProfilePostRequestWithError_returnCorrectModelAndView() {
        UserEditBindingModel model = new UserEditBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "addCategoryInput");
        bindingResult.addError(new ObjectError("Test error", "error"));
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.editProfileConfirm("1", model, bindingResult, modelAndView, redirectAttributes);

        Assert.assertTrue(bindingResult.hasErrors());
        Assert.assertEquals(bindingResult, redirectAttributes.getFlashAttributes().get("org.springframework.validation.BindingResult.userInput"));
        Assert.assertEquals(model, redirectAttributes.getFlashAttributes().get("userInput"));
        Assert.assertEquals("redirect:1", modelAndView.getViewName());
    }

    @Test
    public void adminUserController_editProfilePostRequestWithException_returnCorrectModelAndView() throws PasswordsMismatchException {
        UserEditBindingModel model = new UserEditBindingModel();
        doThrow(new PasswordsMismatchException("")).when(this.userService).editUserData(model, "1");
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "addCategoryInput");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.editProfileConfirm("1", model, bindingResult, modelAndView, redirectAttributes);

        Assert.assertEquals("error", redirectAttributes.getFlashAttributes().get("passwordError"));
        Assert.assertEquals("redirect:1", modelAndView.getViewName());
    }

    @Test
    public void adminUserController_editProfilePostRequestCorrect_returnCorrectModelAndView() {
        UserEditBindingModel model = new UserEditBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "addCategoryInput");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.editProfileConfirm("1", model, bindingResult, modelAndView, redirectAttributes);

        Assert.assertEquals("redirect:../1", modelAndView.getViewName());
    }

    @Test
    public void adminUserController_deleteUserGetRequest_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        this.controller.deleteUser("1", modelAndView);
        UserViewModel expected = new UserViewModel(){{
            setId("1");
            setEmail("pesho@pesho.com");
            setFirstName("Pesho");
        }};
        UserViewModel actual = (UserViewModel) modelAndView.getModelMap().get("userInput");

        Assert.assertEquals("admin-user-delete", modelAndView.getViewName());
        Assert.assertEquals("Delete Profile", modelAndView.getModelMap().get("title"));
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
    }

    @Test
    public void adminUserController_deleteUserGetRequestWithDeletedUser_returnCorrectModelAndView() {
        when(this.userService.getUserServiceModelById("1")).thenReturn(new UserServiceModel(){{
            setId("1");
            setEmail("pesho@pesho.com");
            setFirstName("Pesho");
            setDeletedOn(LocalDate.now());
        }});
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = this.controller.deleteUser("1", modelAndView);

        Assert.assertEquals("redirect:../../../", modelAndView.getViewName());
    }

    @Test
    public void adminUserController_deleteUserPostRequest_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        this.controller.deleteUserConfirm("1", modelAndView);

        Assert.assertEquals("redirect:../all", modelAndView.getViewName());
    }

    @Test
    public void adminUserController_restoreUserGetRequest_returnCorrectModelAndView() {
        when(this.userService.getUserServiceModelById("1")).thenReturn(new UserServiceModel(){{
            setId("1");
            setEmail("pesho@pesho.com");
            setFirstName("Pesho");
            setDeletedOn(LocalDate.now());
        }});
        ModelAndView modelAndView = new ModelAndView();
        this.controller.restoreUser("1", modelAndView);
        UserViewModel expected = new UserViewModel(){{
            setId("1");
            setEmail("pesho@pesho.com");
            setFirstName("Pesho");
        }};
        UserViewModel actual = (UserViewModel) modelAndView.getModelMap().get("userInput");

        Assert.assertEquals("admin-user-restore", modelAndView.getViewName());
        Assert.assertEquals("Restore Profile", modelAndView.getModelMap().get("title"));
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
    }

    @Test
    public void adminUserController_restoreUserGetRequestWithNonDeletedUser_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = this.controller.restoreUser("1", modelAndView);

        Assert.assertEquals("redirect:../../../", modelAndView.getViewName());
    }

    @Test
    public void adminUserController_restoreUserPostRequest_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        this.controller.deleteUserConfirm("1", modelAndView);

        Assert.assertEquals("redirect:../all", modelAndView.getViewName());
    }

    @Test
    public void adminUserController_makeModeratorGetRequestWithModeratorAlready_returnCorrectModelAndView() {
        when(this.userService.isUserModeratorById("1")).thenReturn(true);
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.makeModerator("1", modelAndView, redirectAttributes);

        Assert.assertEquals("error", redirectAttributes.getFlashAttributes().get("moderatorAlreadyError"));
        Assert.assertEquals("redirect:../1", modelAndView.getViewName());
    }

    @Test
    public void adminUserController_makeModeratorGetRequestWithCorrectData_returnCorrectModelAndView() {
        when(this.userService.isUserModeratorById("1")).thenReturn(false);
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.makeModerator("1", modelAndView, redirectAttributes);
        UserViewModel expected = new UserViewModel(){{
            setId("1");
            setEmail("pesho@peshev.com");
            setFirstName("Pesho");
        }};
        UserViewModel actual = (UserViewModel) modelAndView.getModelMap().get("userInput");

        Assert.assertEquals("admin-user-moderator", modelAndView.getViewName());
        Assert.assertEquals("Make Moderator", modelAndView.getModelMap().get("title"));
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
    }

    @Test
    public void adminUserController_makeModeratorPostRequestWithException_returnCorrectModelAndView() throws UserIsModeratorAlreadyException {
        doThrow(new UserIsModeratorAlreadyException("")).when(this.userService).makeModerator("1");
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.makeModeratorConfirm("1", modelAndView, redirectAttributes);

        Assert.assertEquals("error", redirectAttributes.getFlashAttributes().get("moderatorAlreadyError"));
        Assert.assertEquals("redirect:../1", modelAndView.getViewName());
    }

    @Test
    public void adminUserController_makeModeratorPostRequestWithNoException_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.makeModeratorConfirm("1", modelAndView, redirectAttributes);

        Assert.assertEquals("redirect:../1", modelAndView.getViewName());
    }

    @Test
    public void adminUserController_revokeAuthorityGetRequestWithModeratorAlready_returnCorrectModelAndView() {
        when(this.userService.isUserModeratorById("1")).thenReturn(false);
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.revokeAuthority("1", modelAndView, redirectAttributes);

        Assert.assertEquals("error", redirectAttributes.getFlashAttributes().get("notModeratorError"));
        Assert.assertEquals("redirect:../1", modelAndView.getViewName());
    }

    @Test
    public void adminUserController_revokeAuthorityGetRequestWithCorrectData_returnCorrectModelAndView() {
        when(this.userService.isUserModeratorById("1")).thenReturn(true);
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.revokeAuthority("1", modelAndView, redirectAttributes);
        UserViewModel expected = new UserViewModel(){{
            setId("1");
            setEmail("pesho@peshev.com");
            setFirstName("Pesho");
        }};
        UserViewModel actual = (UserViewModel) modelAndView.getModelMap().get("userInput");

        Assert.assertEquals("admin-user-revoke-authority", modelAndView.getViewName());
        Assert.assertEquals("Revoke Moderator Authority", modelAndView.getModelMap().get("title"));
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
    }

    @Test
    public void adminUserController_revokeAuthorityPostRequestWithException_returnCorrectModelAndView() throws UserIsNotModeratorException {
        doThrow(new UserIsNotModeratorException("")).when(this.userService).revokeModeratorAuthority("1");
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.revokeAuthorityConfirm("1", modelAndView, redirectAttributes);

        Assert.assertEquals("error", redirectAttributes.getFlashAttributes().get("notModeratorError"));
        Assert.assertEquals("redirect:../1", modelAndView.getViewName());
    }

    @Test
    public void adminUserController_revokeAuthorityPostRequestWithNoException_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.revokeAuthorityConfirm("1", modelAndView, redirectAttributes);

        Assert.assertEquals("redirect:../1", modelAndView.getViewName());
    }
}