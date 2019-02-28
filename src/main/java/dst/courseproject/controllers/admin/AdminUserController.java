package dst.courseproject.controllers.admin;

import dst.courseproject.exceptions.PasswordsMismatchException;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.service.UserRestoreServiceModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.UserService;
import dst.courseproject.services.VideoService;
import dst.courseproject.util.Users;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        modelAndView.addObject("title", "All Users");
        modelAndView.addObject("userModels", userViewModels);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView userProfile(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserViewModel model = this.userService.getUserViewModelById(id);
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserViewModel loggedUser = this.userService.getUserViewModelByEmail(principal.getUsername());

        Boolean isModeratorUser = Users.hasRole("MODERATOR", model.getAuthorities());
        Boolean isAdminUser = Users.hasRole("ADMIN", model.getAuthorities());
        Boolean isAdminPrincipal = Users.hasRole("ADMIN", principal.getAuthorities());

        Set<VideoViewModel> videoViewModels = this.videoService.mapVideoToModel(model.getVideos());

        modelAndView.setViewName("admin-user-profile");
        modelAndView.addObject("title", model.getFirstName() + "'s Profile");
        modelAndView.addObject("isAdminUser", isAdminUser);
        modelAndView.addObject("isModeratorUser", isModeratorUser);
        modelAndView.addObject("isAdminPrincipal", isAdminPrincipal);
        modelAndView.addObject("user", model);
        modelAndView.addObject("videos", videoViewModels);

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProfile(@PathVariable("id") String id, ModelAndView modelAndView, Model model, ModelMapper mapper) {
        UserServiceModel userServiceModel = this.userService.getUserServiceModelById(id);
        modelAndView.setViewName("admin-user-edit");
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

    @PostMapping("/edit/{id}")
    public ModelAndView editProfileConfirm(@PathVariable("id") String id, @Valid @ModelAttribute(name = "userInput") UserEditBindingModel userEditBindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userInput", bindingResult);
            redirectAttributes.addFlashAttribute("userInput", userEditBindingModel);
            modelAndView.setViewName("redirect:" + id);
        } else {
            try {
                this.userService.editUserData(userEditBindingModel, id);
                modelAndView.setViewName("redirect:../" + id);
            } catch (PasswordsMismatchException e) {
                redirectAttributes.addFlashAttribute("passwordError", "error");
                modelAndView.setViewName("redirect:" + id);
            }
        }

        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserServiceModel userServiceModel = this.userService.getUserServiceModelById(id);
        modelAndView.setViewName("user-delete");
        modelAndView.addObject("title", "Delete Profile");
        modelAndView.addObject("userInput", userServiceModel);

        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteUserConfirm(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.userService.deleteUser(id);
        modelAndView.setViewName("redirect:../all");

        return modelAndView;
    }

    @GetMapping("/restore/{id}")
    public ModelAndView restoreUser(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserRestoreServiceModel userServiceModel = this.userService.getDeletedUserServiceModelById(id);
        modelAndView.setViewName("user-restore");
        modelAndView.addObject("title", "Restore Profile");
        modelAndView.addObject("userInput", userServiceModel);

        return modelAndView;
    }

    @PostMapping("/restore/{id}")
    public ModelAndView restoreUserConfirm(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.userService.restoreUser(id);
        modelAndView.setViewName("redirect:../all");

        return modelAndView;
    }

    @GetMapping("/moderator/{id}")
    public ModelAndView makeModerator(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserViewModel userViewModel = this.userService.getUserViewModelById(id);
        modelAndView.setViewName("user-moderator");
        modelAndView.addObject("title", "Make Moderator");
        modelAndView.addObject("userInput", userViewModel);

        return modelAndView;
    }

    @PostMapping("/moderator/{id}")
    public ModelAndView makeModeratorConfirm(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.userService.makeModerator(id);
        modelAndView.setViewName("redirect:../" + id);

        return modelAndView;
    }
}
