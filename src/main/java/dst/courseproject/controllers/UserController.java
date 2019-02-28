package dst.courseproject.controllers;

import dst.courseproject.exceptions.PasswordsMismatchException;
import dst.courseproject.exceptions.UserAlreadyExistsException;
import dst.courseproject.models.binding.UserRegisterBindingModel;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.UserService;
import dst.courseproject.services.VideoService;
import dst.courseproject.util.Users;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final VideoService videoService;

    @Autowired
    public UserController(UserService userService, VideoService videoService) {
        this.userService = userService;
        this.videoService = videoService;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView, Model model, Principal principal) {
        if (principal != null) {
            return new ModelAndView("redirect:/");
        }
        modelAndView.setViewName("register");
        modelAndView.addObject("title", "Register");
        if (!model.containsAttribute("registerInput")) {
            model.addAttribute("registerInput", new UserRegisterBindingModel());
        }
        if (model.containsAttribute("passwordError")) {
            modelAndView.addObject("passwordError");
        }
        if (model.containsAttribute("userExistsError")) {
            modelAndView.addObject("userExistsError");
        }

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute(name = "registerInput") UserRegisterBindingModel userBindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerInput", bindingResult);
            redirectAttributes.addFlashAttribute("registerInput", userBindingModel);
            modelAndView.setViewName("redirect:register");
        } else {
            try {
                this.userService.register(userBindingModel);
                modelAndView.setViewName("redirect:login");
                redirectAttributes.addFlashAttribute("success", "Successfully registered");
            } catch (PasswordsMismatchException e) {
                redirectAttributes.addFlashAttribute("passwordError", "error");
                modelAndView.setViewName("redirect:register");
            } catch (UserAlreadyExistsException e) {
                redirectAttributes.addFlashAttribute("userExistsError", "error");
                modelAndView.setViewName("redirect:register");
            }
        }

        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, Model model, Principal principal) {
        if (principal != null) {
            return new ModelAndView("redirect:/");
        }
        modelAndView.setViewName("login");
        modelAndView.addObject("title", "Login");
        if (model.containsAttribute("success")) {
            modelAndView.addObject("success");
        }

        return modelAndView;
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(ModelAndView modelAndView) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserViewModel userViewModel = this.userService.getUserViewModelByEmail(userDetails.getUsername());
        Boolean isAdmin = Users.hasRole("ADMIN", userViewModel.getAuthorities());
        Boolean isModerator = Users.hasRole("MODERATOR", userViewModel.getAuthorities());

        Set<VideoViewModel> videoViewModels = this.videoService.mapVideoToModel(userViewModel.getVideos());

        modelAndView.setViewName("user-profile");
        modelAndView.addObject("user", userViewModel);
        modelAndView.addObject("isAdmin", isAdmin);
        modelAndView.addObject("isModerator", isModerator);
        modelAndView.addObject("videos", videoViewModels);

        return modelAndView;
    }

    @GetMapping("/profile/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(ModelAndView modelAndView, Model model, ModelMapper mapper, Principal principal) {
        UserServiceModel userServiceModel = this.userService.getUserServiceModelByEmail(principal.getName());
        modelAndView.setViewName("user-edit");
        modelAndView.addObject("title", "Edit Profile");

        if (!model.containsAttribute("userInput")) {
            UserEditBindingModel userEditBindingModel = mapper.map(userServiceModel, UserEditBindingModel.class);
            model.addAttribute("userInput", userEditBindingModel);
        }
        if (model.containsAttribute("passwordError")) {
            modelAndView.addObject("passwordError");
        }

        return modelAndView;
    }

    @PostMapping("/profile/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(@Valid @ModelAttribute(name = "userInput") UserEditBindingModel userEditBindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userInput", bindingResult);
            redirectAttributes.addFlashAttribute("userInput", userEditBindingModel);
            modelAndView.setViewName("redirect:/users/profile/edit");
        } else {
            try {
                this.userService.editUserDataByEmail(userEditBindingModel, principal.getName());
                modelAndView.setViewName("redirect:../profile");
            } catch (PasswordsMismatchException e) {
                redirectAttributes.addFlashAttribute("passwordError", "error");
                modelAndView.setViewName("redirect:/users/profile/edit");
            }
        }

        return modelAndView;
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView logout(@RequestParam(required = false, name = "logout") String logout, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        modelAndView.setViewName("redirect:/login");

        if(logout != null) {
            redirectAttributes.addFlashAttribute("logout", logout);
        }

        return modelAndView;
    }
}
