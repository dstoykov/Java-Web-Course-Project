package dst.courseproject.controllers;

import dst.courseproject.cloud.CloudVideoUploader;
import dst.courseproject.entities.Category;
import dst.courseproject.models.binding.AddVideoBindingModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.CategoryService;
import dst.courseproject.services.VideoService;
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

@Controller
@RequestMapping("/videos")
public class VideoController {
    private final VideoService videoService;
    private final CategoryService categoryService;
    private final CloudVideoUploader videoUploader;

    @Autowired
    public VideoController(VideoService videoService, CategoryService categoryService, CloudVideoUploader videoUploader) {
        this.videoService = videoService;
        this.categoryService = categoryService;
        this.videoUploader = videoUploader;
    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView modelAndView, Model model) {
        modelAndView.setViewName("video-add");
        if (!model.containsAttribute("videoInput")) {
            model.addAttribute("videoInput", new AddVideoBindingModel());
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
    public ModelAndView add(@Valid @ModelAttribute(name = "videoInput") AddVideoBindingModel addVideoBindingModel, ModelAndView modelAndView, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors()) {
            System.out.println(addVideoBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.videoInput", bindingResult);
            redirectAttributes.addFlashAttribute("videoInput", addVideoBindingModel);
            modelAndView.setViewName("redirect:add");
        } else {
            this.videoService.addVideo(addVideoBindingModel, principal);
            modelAndView.setViewName("redirect:../../");
        }

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView videoDetails(@PathVariable String id, ModelAndView modelAndView, Model model) {
        VideoViewModel videoViewModel = this.videoService.getVideoViewModelForDetailsById(id);

        modelAndView.setViewName("video-details");
        modelAndView.addObject("videoName", videoViewModel.getTitle());
        modelAndView.addObject("source", videoViewModel.getUrl());
        modelAndView.addObject("uploaderName", videoViewModel.getAuthor().getFirstName() + " " + videoViewModel.getAuthor().getLastName());
        modelAndView.addObject("uploadDate", videoViewModel.getUploadedOn());
        modelAndView.addObject("description", videoViewModel.getDescription());
        modelAndView.addObject("category", videoViewModel.getCategory().getName());
        modelAndView.addObject("comments", videoViewModel.getComments());
        modelAndView.addObject("views", videoViewModel.getViews());
        modelAndView.addObject("likes", videoViewModel.getLikes());
        modelAndView.addObject("dislikes", videoViewModel.getDislikes());

        return modelAndView;
    }
}
