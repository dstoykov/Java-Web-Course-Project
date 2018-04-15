package dst.courseproject.controllers.admin;

import dst.courseproject.exception.PasswordsMismatchException;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.UserService;
import dst.courseproject.services.VideoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;
    private final VideoService videoService;

    @Autowired
    public AdminUserController(UserService userService, VideoService videoService) {
        this.userService = userService;
        this.videoService = videoService;
    }

    @GetMapping("/all")
    public ModelAndView allUsers(ModelAndView modelAndView, Principal principal) {
        List<UserViewModel> userViewModels = this.userService.getListWithViewModels(principal.getName());
        modelAndView.setViewName("users-all");
        modelAndView.addObject("userModels", userViewModels);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView userProfile(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserViewModel model = this.userService.getUserViewModelById(id);

        Set<VideoViewModel> videoViewModels = this.videoService.mapVideoToModel(model.getVideos());

        modelAndView.setViewName("user-profile");
        modelAndView.addObject("user", model);
        modelAndView.addObject("videos", videoViewModels);

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProfile(@PathVariable("id") String id, ModelAndView modelAndView, Model model, ModelMapper mapper) {
        UserServiceModel userServiceModel = this.userService.getUserServiceModelById(id);
        modelAndView.setViewName("user-edit");

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
        } else {
            try {
                this.userService.editUserData(userEditBindingModel, id);
                modelAndView.setViewName("redirect:../profile");
            } catch (PasswordsMismatchException e) {
                modelAndView.setViewName("redirect:/users/edit/" + id);
            }
        }

        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserServiceModel userServiceModel = this.userService.getUserServiceModelById(id);
        modelAndView.setViewName("user-delete");
        modelAndView.addObject("userInput", userServiceModel);

        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteUserConfirm(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.userService.deleteUser(id);
        modelAndView.setViewName("redirect:all");

        return modelAndView;
    }
}
