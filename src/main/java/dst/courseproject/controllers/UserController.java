package dst.courseproject.controllers;

import dst.courseproject.models.binding.RegisterUserBindingModel;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.UserService;
import dst.courseproject.services.VideoService;
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
import java.util.List;
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
    @PreAuthorize("!isAuthenticated()")
    public ModelAndView register(ModelAndView modelAndView, Model model) {
        modelAndView.setViewName("register");
        if (!model.containsAttribute("registerInput")) {
            model.addAttribute("registerInput", new RegisterUserBindingModel());
        }

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute(name = "registerInput") RegisterUserBindingModel userBindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerInput", bindingResult);
            redirectAttributes.addFlashAttribute("registerInput", userBindingModel);
            modelAndView.setViewName("redirect:register");
        } else if (!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())) {
            modelAndView.setViewName("redirect:register");
        } else {
            this.userService.register(userBindingModel);
            modelAndView.setViewName("redirect:login");
        }

        return modelAndView;
    }

    @GetMapping("/login")
    @PreAuthorize("!isAuthenticated()")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(ModelAndView modelAndView) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserViewModel userViewModel = this.userService.getUserModelByEmail(userDetails.getUsername());

        Set<VideoViewModel> videoViewModels = this.videoService.mapVideoToModel(userViewModel.getVideos());

        modelAndView.setViewName("user-profile");
        modelAndView.addObject("user", userViewModel);
        modelAndView.addObject("videos", videoViewModels);

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(@PathVariable("id") String id, ModelAndView modelAndView, Model model, ModelMapper mapper) {
        UserServiceModel userServiceModel = this.userService.getUserModelById(id);
        modelAndView.setViewName("edit-profile");

        if (!model.containsAttribute("userInput")) {
            UserEditBindingModel userEditBindingModel = mapper.map(userServiceModel, UserEditBindingModel.class);
            model.addAttribute("userInput", userEditBindingModel);
        }

        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editProfile(@PathVariable("id") String id, @Valid @ModelAttribute(name = "userInput") UserEditBindingModel userEditBindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userInput", bindingResult);
            redirectAttributes.addFlashAttribute("userInput", userEditBindingModel);
            modelAndView.setViewName("redirect:/users/edit/" + id);
        } else if (!userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())) {
            modelAndView.setViewName("redirect:/users/edit/" + id);
        } else {
            this.userService.editUserData(userEditBindingModel, id);
            modelAndView.setViewName("redirect:../profile");
        }

        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.setViewName("delete-profile");

        return modelAndView;
    }

    @PostMapping("delete/{id}")
    public ModelAndView deleteUserConfirm(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.userService.deleteUser(id);
        modelAndView.setViewName("redirect:all");

        return modelAndView;
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView userProfile(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserServiceModel userServiceModel = this.userService.getUserModelById(id);

        Set<VideoViewModel> videoViewModels = this.videoService.mapVideoToModel(userServiceModel.getVideos());

        modelAndView.setViewName("user-profile");
        modelAndView.addObject("user", userServiceModel);
        modelAndView.addObject("videos", videoViewModels);

        return modelAndView;
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView logout(@RequestParam(required = false, name = "logout") String logout, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        modelAndView.setViewName("redirect:/login");

        if(logout != null) redirectAttributes.addFlashAttribute("logout", logout);

        return modelAndView;
    }
}
