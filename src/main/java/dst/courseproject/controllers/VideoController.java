package dst.courseproject.controllers;

import dst.courseproject.cloud.CloudVideoUploader;
import dst.courseproject.entities.Category;
import dst.courseproject.models.binding.AddVideoBindingModel;
import dst.courseproject.models.binding.RegisterUserBindingModel;
import dst.courseproject.services.CategoryService;
import dst.courseproject.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        modelAndView.setViewName("add-video");
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
}
