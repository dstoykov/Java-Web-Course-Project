package dst.courseproject.controllers;

import com.dropbox.core.DbxException;
import dst.courseproject.cloud.DropboxService;
import dst.courseproject.models.binding.VideoAddBindingModel;
import dst.courseproject.models.binding.VideoEditBindingModel;
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

        Set<String> categoriesNames = this.categoryService.getCategoriesNames();
        modelAndView.addObject("title", "Add Video");
        modelAndView.addObject("categories", categoriesNames);

        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView add(@Valid @ModelAttribute(name = "videoInput") VideoAddBindingModel videoAddBindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.videoInput", bindingResult);
            redirectAttributes.addFlashAttribute("videoInput", videoAddBindingModel);
            modelAndView.setViewName("redirect:add");
        } else {
            try {
                this.videoService.addVideo(videoAddBindingModel, principal);
            } catch (DbxException | IOException e) {
                e.printStackTrace();
            } //TODO handle FileUploadBase.SizeLimitExceededException
            modelAndView.setViewName("redirect:../");
        }

        return modelAndView;
    }

    @GetMapping("/{identifier}")
    public ModelAndView videoDetails(@PathVariable String identifier, ModelAndView modelAndView, Principal principal) throws DbxException {
        VideoViewModel videoViewModel = this.videoService.getVideoViewModel(identifier);
        this.videoService.increaseVideoViewsByOne(identifier);
        Set<VideoViewModel> videosFromSameUser = this.videoService.getLastTenVideosByUserAsViewModelsExceptCurrent(this.modelMapper.map(videoViewModel.getAuthor(), UserServiceModel.class), videoViewModel.getVideoIdentifier());
        Set<CommentViewModel> comments = this.commentService.getCommentViewModelsByVideo(identifier);
        UserServiceModel userServiceModel = this.userService.getUserServiceModelByEmail(principal.getName());
        String principalName = UserUtils.getUserFullName(userServiceModel);
        Boolean isModerator = UserUtils.hasRole("MODERATOR", userServiceModel.getAuthorities());
        Boolean isLiked = videoViewModel.getUsersLiked().containsKey(userServiceModel.getId());
        String videoFileUrl = "";

        videoFileUrl = this.dropboxService.getFileLink(videoViewModel.getVideoIdentifier());

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

    @GetMapping("/edit/{identifier}")
    public ModelAndView editVideo(@PathVariable String identifier, ModelAndView modelAndView, Model model) {
        modelAndView.setViewName("video-edit");
        Set<String> categoriesNames = this.categoryService.getCategoriesNames();

        modelAndView.addObject("title", "Edit Video");
        if (!model.containsAttribute("videoInput")) {
            VideoViewModel viewModel = this.videoService.getVideoViewModel(identifier);
            VideoEditBindingModel bindingModel = this.modelMapper.map(viewModel, VideoEditBindingModel.class);
            bindingModel.setCategory(viewModel.getCategory().getName());
            bindingModel.setIdentifier(viewModel.getVideoIdentifier());
            model.addAttribute("videoInput", bindingModel);
        }
        modelAndView.addObject("categories", categoriesNames);

        return modelAndView;
    }

    @PostMapping("/edit/{identifier}")
    public ModelAndView editVideo(@PathVariable String identifier, @Valid @ModelAttribute(name = "videoInput") VideoEditBindingModel videoEditBindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        System.out.println();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.videoInput", bindingResult);
            redirectAttributes.addFlashAttribute("videoInput", videoEditBindingModel);
            modelAndView.setViewName("redirect:" + identifier);
        } else {
            this.videoService.editVideoData(videoEditBindingModel, identifier);
            modelAndView.setViewName("redirect:../" + identifier);
        }

        return modelAndView;
    }

    @GetMapping("/delete/{identifier}")
    public ModelAndView deleteVideo(@PathVariable String identifier, ModelAndView modelAndView) {
        VideoViewModel viewModel = this.videoService.getVideoViewModel(identifier);

        modelAndView.setViewName("video-delete");
        modelAndView.addObject("title", "Delete Video");
        modelAndView.addObject("videoInput", viewModel);

        return modelAndView;
    }

    @PostMapping("/delete/{identifier}")
    public ModelAndView deleteVideoConfirm(@PathVariable String identifier, ModelAndView modelAndView) {
        this.videoService.deleteVideo(identifier);
        modelAndView.setViewName("redirect:../../");

        return modelAndView;
    }
}
