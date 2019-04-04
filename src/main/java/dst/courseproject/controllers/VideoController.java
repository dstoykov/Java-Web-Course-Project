package dst.courseproject.controllers;

import com.dropbox.core.DbxException;
import dst.courseproject.cloud.DropboxService;
import dst.courseproject.entities.Category;
import dst.courseproject.models.binding.VideoAddBindingModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.CommentViewModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.CategoryService;
import dst.courseproject.services.CommentService;
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
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final UserService userService;
    private final DropboxService dropboxService;
    private final ModelMapper modelMapper;

    @Autowired
    public VideoController(VideoService videoService, CategoryService categoryService, CommentService commentService, UserService userService, DropboxService dropboxService, ModelMapper modelMapper) {
        this.videoService = videoService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.userService = userService;
        this.dropboxService = dropboxService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView modelAndView, Model model) {
        modelAndView.setViewName("video-add");
        if (!model.containsAttribute("videoInput")) {
            model.addAttribute("videoInput", new VideoAddBindingModel());
        }

        modelAndView.addObject("title", "Add Video");
        List<Category> categories = this.categoryService.getAllCategories();
        List<String> categoriesNames = new ArrayList<>();
        for (Category category : categories) {
            categoriesNames.add(category.getName());
        }
        modelAndView.addObject("categories", categoriesNames);

        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView add(@Valid @ModelAttribute(name = "videoInput") VideoAddBindingModel videoAddBindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {
            System.out.println(videoAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.videoInput", bindingResult);
            redirectAttributes.addFlashAttribute("videoInput", videoAddBindingModel);
            modelAndView.setViewName("redirect:add");
        } else {
            try {
                this.videoService.addVideo(videoAddBindingModel, principal);
            } catch (DbxException | IOException e) {
                e.printStackTrace();
            } //TODO catch FileUploadBase.SizeLimitExceededException
            modelAndView.setViewName("redirect:../");
        }

        return modelAndView;
    }

    @GetMapping("/{identifier}")
    public ModelAndView videoDetails(@PathVariable String identifier, ModelAndView modelAndView, Principal principal) {
        VideoViewModel videoViewModel = this.videoService.getVideoViewModelForDetailsByIdentifier(identifier);
        Set<VideoViewModel> videosFromSameUser = this.videoService.getLastTenVideosByUserAsViewModelsExceptCurrent(this.modelMapper.map(videoViewModel.getAuthor(), UserServiceModel.class), videoViewModel.getVideoIdentifier());
        Set<CommentViewModel> comments = this.commentService.getCommentViewModelsByVideo(identifier);
        UserServiceModel userServiceModel = this.userService.getUserServiceModelByEmail(principal.getName());
        String principalName = UserUtils.getUserFullName(userServiceModel);
        Boolean isModerator = UserUtils.hasRole("MODERATOR", userServiceModel.getAuthorities());
        Boolean isLiked = videoViewModel.getUsersLiked().containsKey(userServiceModel.getId());
        String videoFileUrl = "";

//        try {
//            videoFileUrl = this.dropboxService.getFileLink(videoViewModel.getVideoIdentifier());
//        } catch (DbxException e) {
//            e.printStackTrace();
//        }

        modelAndView.setViewName("video-details");
        modelAndView.addObject("video", videoViewModel);
        modelAndView.addObject("videosFromSameUser", videosFromSameUser);
        modelAndView.addObject("comments", comments);
        modelAndView.addObject("principalName", principalName);
        modelAndView.addObject("isModerator", isModerator);
        modelAndView.addObject("videoFileUrl", videoFileUrl);
        modelAndView.addObject("isLiked", isLiked);

        return modelAndView;
    }
}
