package dst.courseproject.controllers;

import com.sun.security.auth.UserPrincipal;
import dst.courseproject.entities.Role;
import dst.courseproject.exceptions.InvalidReCaptchaException;
import dst.courseproject.exceptions.PasswordsMismatchException;
import dst.courseproject.exceptions.UserAlreadyExistsException;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.binding.UserRegisterBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.recaptcha.ReCaptchaService;
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
import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
@SuppressWarnings("unchecked")
public class UserControllerTests {
    private UserService userService;
    private VideoService videoService;
    private ReCaptchaService reCaptchaService;
    private UserController controller;

    @Before
    public void init() {
        this.userService = mock(UserService.class);
        this.videoService = mock(VideoService.class);
        this.reCaptchaService = mock(ReCaptchaService.class);
        this.controller = new UserController(this.userService, this.videoService, this.reCaptchaService, new ModelMapper());
        when(this.userService.getUserServiceModelByEmail("pesho@pesho.com")).thenReturn(new UserServiceModel(){{
            setId("1");
            setEmail("pesho@peshev.com");
            setFirstName("Pesho");
            setLastName("Peshev");
            setAuthorities(new HashSet<>(){{
                add(new Role(){{
                    setAuthority("USER");
                }});
                add(new Role(){{
                    setAuthority("MODERATOR");
                }});
            }});
        }});
        when(this.userService.getUserViewModelByEmail("pesho@pesho.com")).thenReturn(new UserViewModel(){{
            setId("1");
            setEmail("pesho@peshev.com");
            setFirstName("Pesho");
            setLastName("Peshev");
            setAuthorities(new HashSet<>(){{
                add(new Role(){{
                    setAuthority("USER");
                }});
                add(new Role(){{
                    setAuthority("MODERATOR");
                }});
            }});
        }});
        when(this.videoService.getVideosByUserAsViewModels(new UserServiceModel(){{
            setEmail("pesho@pesho.com");
        }})).thenReturn(new LinkedHashSet<>(){{
            add(new VideoViewModel(){{
                setTitle("Video1");
            }});
            add(new VideoViewModel(){{
                setTitle("Video2");
            }});
        }});
    }

    @Test
    public void userController_registerGetRequestWithNullPrincipal_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        this.controller.register(modelAndView, model, null);

        Assert.assertEquals("register", modelAndView.getViewName());
        Assert.assertEquals("Register", modelAndView.getModelMap().get("title"));
        Assert.assertNotNull(model.asMap().get("registerInput"));
    }

    @Test
    public void userController_registerGetRequestWithActualPrincipal_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        Principal principal = new UserPrincipal("pesho@mail.com");
        modelAndView = this.controller.register(modelAndView, model, principal);

        Assert.assertEquals("redirect:/", modelAndView.getViewName());
    }

    @Test
    public void userController_registerPostRequestWithErrors_returnCorrectModelAndView() {
        UserRegisterBindingModel userRegisterBindingModel = new UserRegisterBindingModel();
        BindingResult bindingResult = new BeanPropertyBindingResult(userRegisterBindingModel, "registerInput");
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        bindingResult.addError(new ObjectError("err", "err"));
        this.controller.register(userRegisterBindingModel, bindingResult, modelAndView, redirectAttributes, "");

        Assert.assertEquals(bindingResult, redirectAttributes.getFlashAttributes().get("org.springframework.validation.BindingResult.registerInput"));
        Assert.assertEquals(userRegisterBindingModel, redirectAttributes.getFlashAttributes().get("registerInput"));
        Assert.assertEquals("redirect:register", modelAndView.getViewName());
    }

    @Test
    public void userController_registerPostRequestWithPasswordsMismatchException_returnCorrectModelAndView() throws PasswordsMismatchException, UserAlreadyExistsException {
        UserRegisterBindingModel bindingModel = new UserRegisterBindingModel();
        doThrow(new PasswordsMismatchException("")).when(this.userService).register(bindingModel);
        BindingResult bindingResult = new BeanPropertyBindingResult(bindingModel, "registerInput");
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.register(bindingModel, bindingResult, modelAndView, redirectAttributes, "");

        Assert.assertEquals("error", redirectAttributes.getFlashAttributes().get("passwordError"));
        Assert.assertEquals(bindingModel, redirectAttributes.getFlashAttributes().get("registerInput"));
        Assert.assertEquals("redirect:register", modelAndView.getViewName());
    }

    @Test
    public void userController_registerPostRequestWithUserAlreadyExistException_returnCorrectModelAndView() throws PasswordsMismatchException, UserAlreadyExistsException {
        UserRegisterBindingModel bindingModel = new UserRegisterBindingModel();
        doThrow(new UserAlreadyExistsException("")).when(this.userService).register(bindingModel);
        BindingResult bindingResult = new BeanPropertyBindingResult(bindingModel, "registerInput");
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.register(bindingModel, bindingResult, modelAndView, redirectAttributes, "");

        Assert.assertEquals("error", redirectAttributes.getFlashAttributes().get("userExistsError"));
        Assert.assertEquals("redirect:register", modelAndView.getViewName());
    }

    @Test
    public void userController_registerPostRequestWithReCaptchaException_returnCorrectModelAndView() throws InvalidReCaptchaException {
        UserRegisterBindingModel bindingModel = new UserRegisterBindingModel();
        doThrow(new InvalidReCaptchaException("")).when(this.reCaptchaService).captcha("");
        BindingResult bindingResult = new BeanPropertyBindingResult(bindingModel, "registerInput");
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.register(bindingModel, bindingResult, modelAndView, redirectAttributes, "");

        Assert.assertEquals("error", redirectAttributes.getFlashAttributes().get("invalidCaptchaError"));
        Assert.assertEquals(bindingModel, redirectAttributes.getFlashAttributes().get("registerInput"));
        Assert.assertEquals("redirect:register", modelAndView.getViewName());
    }

    @Test
    public void userController_registerPostRequestWithValidData_returnCorrectModelAndView() {
        UserRegisterBindingModel bindingModel = new UserRegisterBindingModel();
        BindingResult bindingResult = new BeanPropertyBindingResult(bindingModel, "registerInput");
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.register(bindingModel, bindingResult, modelAndView, redirectAttributes, "");

        Assert.assertEquals("redirect:login", modelAndView.getViewName());
    }

    @Test
    public void userController_loginGetRequestWithNullPrincipal_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        this.controller.login(modelAndView, model, null);

        Assert.assertEquals("login", modelAndView.getViewName());
        Assert.assertEquals("Login", modelAndView.getModelMap().get("title"));
    }

    @Test
    public void userController_loginGetRequestWithActualPrincipal_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        Principal principal = new UserPrincipal("pesho@mail.com");
        modelAndView = this.controller.login(modelAndView, model, principal);

        Assert.assertEquals("redirect:/", modelAndView.getViewName());
    }

    @Test
    public void userController_editProfileGetRequest_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        this.controller.editProfile(modelAndView, model, new ModelMapper(), new UserPrincipal("pesho@pesho.com"));
        UserEditBindingModel expected = new UserEditBindingModel(){{
            setId("1");
            setFirstName("Pesho");
        }};
        UserEditBindingModel actual = (UserEditBindingModel) model.asMap().get("userInput");

        Assert.assertEquals("user-edit", modelAndView.getViewName());
        Assert.assertEquals("Edit Profile", modelAndView.getModelMap().get("title"));
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
    }

    @Test
    public void userController_editProfilePostRequestWithError_returnCorrectModelAndView() {
        UserEditBindingModel model = new UserEditBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "addCategoryInput");
        bindingResult.addError(new ObjectError("Test error", "error"));
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.editProfile(model, bindingResult, modelAndView, redirectAttributes, new UserPrincipal("pesho@pesho.com"));

        Assert.assertTrue(bindingResult.hasErrors());
        Assert.assertEquals(bindingResult, redirectAttributes.getFlashAttributes().get("org.springframework.validation.BindingResult.userInput"));
        Assert.assertEquals(model, redirectAttributes.getFlashAttributes().get("userInput"));
        Assert.assertEquals("redirect:/users/profile/edit", modelAndView.getViewName());
    }

    @Test
    public void userController_editProfilePostRequestWithException_returnCorrectModelAndView() throws PasswordsMismatchException {
        UserEditBindingModel model = new UserEditBindingModel();
        doThrow(new PasswordsMismatchException("")).when(this.userService).editUserDataByEmail(model, "pesho@pesho.com");
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "addCategoryInput");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.editProfile(model, bindingResult, modelAndView, redirectAttributes, new UserPrincipal("pesho@pesho.com"));

        Assert.assertEquals("error", redirectAttributes.getFlashAttributes().get("passwordError"));
        Assert.assertEquals("redirect:/users/profile/edit", modelAndView.getViewName());
    }

    @Test
    public void userController_editProfilePostRequestCorrect_returnCorrectModelAndView() {
        UserEditBindingModel model = new UserEditBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "addCategoryInput");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.editProfile(model, bindingResult, modelAndView, redirectAttributes, new UserPrincipal("pesho@pesho.com"));

        Assert.assertEquals("redirect:../profile", modelAndView.getViewName());
    }

    @Test
    public void userController_viewOtherProfileGetRequestWithEqualEmail_returnCorrectModelAndVIew() {
        ModelAndView modelAndView = new ModelAndView();
        Principal principal = new UserPrincipal("pesho@pesho.bg");
        this.controller.viewOtherProfile("cGVzaG9AcGVzaG8uYmc=", modelAndView, principal);

        Assert.assertEquals("redirect:profile", modelAndView.getViewName());
    }

    @Test
    public void userController_viewOtherProfileGetRequestWithNotEqualEmail_returnCorrectModelAndVIew() {
        ModelAndView modelAndView = new ModelAndView();
        Principal principal = new UserPrincipal("pesho@pesho.bg");
        this.controller.viewOtherProfile("cGVzaG9AcGVzaG8uY29t", modelAndView, principal);
        UserViewModel expected = new UserViewModel(){{
            setId("1");
            setEmail("pesho@peshev.com");
            setFirstName("Pesho");
            setLastName("Peshev");
            setAuthorities(new HashSet<>(){{
                add(new Role(){{
                    setAuthority("USER");
                }});
                add(new Role(){{
                    setAuthority("MODERATOR");
                }});
            }});
        }};
        List<VideoViewModel> expectedVideos = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setTitle("Video1");
            }});
            add(new VideoViewModel(){{
                setTitle("Video2");
            }});
        }};
        UserViewModel actual = (UserViewModel) modelAndView.getModelMap().get("user");
        Set<VideoViewModel> actualVideos = (Set<VideoViewModel>) modelAndView.getModelMap().get("videos");
        int i = 0;

        Assert.assertEquals("user-other-user-profile", modelAndView.getViewName());
        Assert.assertEquals("Pesho Peshev", modelAndView.getModelMap().get("title"));
        Assert.assertTrue((Boolean) modelAndView.getModelMap().get("isModeratorUser"));
        Assert.assertFalse((Boolean) modelAndView.getModelMap().get("isAdminUser"));
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        for (VideoViewModel actualVideo : actualVideos) {
            Assert.assertEquals(expectedVideos.get(i++).getTitle(), actualVideo.getTitle());
        }
    }

    @Test
    public void userController_viewOtherProfileGetRequestWithNoPrincipal_returnCorrectModelAndVIew() {
        ModelAndView modelAndView = new ModelAndView();
        this.controller.viewOtherProfile("cGVzaG9AcGVzaG8uY29t", modelAndView, null);
        UserViewModel expected = new UserViewModel(){{
            setId("1");
            setEmail("pesho@peshev.com");
            setFirstName("Pesho");
            setLastName("Peshev");
            setAuthorities(new HashSet<>(){{
                add(new Role(){{
                    setAuthority("USER");
                }});
                add(new Role(){{
                    setAuthority("MODERATOR");
                }});
            }});
        }};
        List<VideoViewModel> expectedVideos = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setTitle("Video1");
            }});
            add(new VideoViewModel(){{
                setTitle("Video2");
            }});
        }};
        UserViewModel actual = (UserViewModel) modelAndView.getModelMap().get("user");
        Set<VideoViewModel> actualVideos = (Set<VideoViewModel>) modelAndView.getModelMap().get("videos");
        int i = 0;

        Assert.assertEquals("user-other-user-profile", modelAndView.getViewName());
        Assert.assertEquals("Pesho Peshev", modelAndView.getModelMap().get("title"));
        Assert.assertTrue((Boolean) modelAndView.getModelMap().get("isModeratorUser"));
        Assert.assertFalse((Boolean) modelAndView.getModelMap().get("isAdminUser"));
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        for (VideoViewModel actualVideo : actualVideos) {
            Assert.assertEquals(expectedVideos.get(i++).getTitle(), actualVideo.getTitle());
        }
    }

    @Test
    public void userController_logoutPostRequestWithNullLogout_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.logout(null, modelAndView, redirectAttributes);

        Assert.assertEquals("redirect:/login", modelAndView.getViewName());
    }

    @Test
    public void userController_logoutPostRequestWithLogout_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.logout("logout", modelAndView, redirectAttributes);

        Assert.assertEquals("redirect:/login", modelAndView.getViewName());
        Assert.assertEquals("logout", redirectAttributes.getFlashAttributes().get("logout"));
    }
}