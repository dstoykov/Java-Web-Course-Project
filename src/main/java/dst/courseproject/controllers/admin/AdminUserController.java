package dst.courseproject.controllers.admin;

import dst.courseproject.exceptions.PasswordsMismatchException;
import dst.courseproject.exceptions.UserIsModeratorAlreadyException;
import dst.courseproject.exceptions.UserIsNotModeratorException;
import dst.courseproject.models.binding.UserEditBindingModel;
import dst.courseproject.models.service.UserRestoreServiceModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.UserViewModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.UserService;
import dst.courseproject.services.VideoService;
import dst.courseproject.util.UserUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final ModelMapper modelMapper;

    @Autowired
    public AdminUserController(UserService userService, VideoService videoService, ModelMapper modelMapper) {
        this.userService = userService;
        this.videoService = videoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ModelAndView allUsers(ModelAndView modelAndView, Principal principal) {
        List<UserViewModel> userViewModels = this.userService.getListWithViewModels(principal.getName());
        modelAndView.setViewName("admin-users-all");
        modelAndView.addObject("title", "All UserUtils");
        modelAndView.addObject("userModels", userViewModels);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView userProfile(@PathVariable("id") String id, ModelAndView modelAndView, Model model) {
        UserViewModel userViewModel = this.userService.getUserViewModelById(id);
        Set<VideoViewModel> videoViewModels = this.videoService.getVideosByUserAsViewModels(this.modelMapper.map(userViewModel, UserServiceModel.class));

        Boolean isModeratorUser = UserUtils.hasRole("MODERATOR", userViewModel.getAuthorities());
        Boolean isAdminUser = UserUtils.hasRole("ADMIN", userViewModel.getAuthorities());

        modelAndView.setViewName("admin-user-profile");
        modelAndView.addObject("title", userViewModel.getFirstName() + "'s Profile");
        modelAndView.addObject("isAdminUser", isAdminUser);
        modelAndView.addObject("isModeratorUser", isModeratorUser);
        modelAndView.addObject("user", userViewModel);
        modelAndView.addObject("videos", videoViewModels);

        if (model.containsAttribute("moderatorAlreadyError")) {
            modelAndView.addObject("moderatorAlreadyError");
        }
        if (model.containsAttribute("notModeratorError")) {
            modelAndView.addObject("notModeratorError");
        }

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
        modelAndView.setViewName("admin-user-delete");
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
        UserServiceModel userServiceModel = this.userService.getUserServiceModelById(id);
        modelAndView.setViewName("admin-user-restore");
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
    public ModelAndView makeModerator(@PathVariable("id") String id, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (this.userService.isUserModerator(id)) {
            redirectAttributes.addFlashAttribute("moderatorAlreadyError", "error");
            modelAndView.setViewName("redirect:../" + id);
        } else {
            UserViewModel userViewModel = this.userService.getUserViewModelById(id);
            modelAndView.setViewName("admin-user-moderator");
            modelAndView.addObject("title", "Make Moderator");
            modelAndView.addObject("userInput", userViewModel);
        }
        return modelAndView;
    }

    @PostMapping("/moderator/{id}")
    public ModelAndView makeModeratorConfirm(@PathVariable("id") String id, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        try {
            this.userService.makeModerator(id);
        } catch (UserIsModeratorAlreadyException e) {
            redirectAttributes.addFlashAttribute("moderatorAlreadyError", "error");
        } finally {
            modelAndView.setViewName("redirect:../" + id);
        }

        return modelAndView;
    }

    @GetMapping("/moderator-revoke/{id}")
    public ModelAndView revokeAuthority(@PathVariable("id") String id, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (!this.userService.isUserModerator(id)) {
            redirectAttributes.addFlashAttribute("notModeratorError", "error");
            modelAndView.setViewName("redirect:../" + id);
        } else {
            UserViewModel userViewModel = this.userService.getUserViewModelById(id);
            modelAndView.setViewName("admin-user-revoke-authority");
            modelAndView.addObject("title", "Revoke Moderator Authority");
            modelAndView.addObject("userInput", userViewModel);
        }

        return modelAndView;
    }

    @PostMapping("/moderator-revoke/{id}")
    public ModelAndView revokeAuthorityConfirm(@PathVariable("id") String id, ModelAndView modelAndView, RedirectAttributes redirect) {
        try {
            this.userService.revokeModeratorAuthority(id);
        } catch (UserIsNotModeratorException e) {
            redirect.addFlashAttribute("notModeratorError", "error");
        } finally {
            modelAndView.setViewName("redirect:../" + id);
        }

        return modelAndView;
    }
}
