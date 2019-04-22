package dst.courseproject.controllers.admin;

import dst.courseproject.models.binding.VideoEditBindingModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.CategoryService;
import dst.courseproject.services.VideoService;
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
import java.util.Set;

@Controller
@RequestMapping("/admin/videos")
public class AdminVideoController {
    private final VideoService videoService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminVideoController(VideoService videoService, CategoryService categoryService, ModelMapper modelMapper) {
        this.videoService = videoService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/edit/{identifier}")
    public ModelAndView editVideo(@PathVariable String identifier, ModelAndView modelAndView, Model model) {
        VideoViewModel viewModel = this.videoService.getVideoViewModel(identifier);

        modelAndView.setViewName("video-edit");
        Set<String> categoriesNames = this.categoryService.getCategoriesNames();

        modelAndView.addObject("title", "Edit Video");
        if (!model.containsAttribute("videoInput")) {
            VideoEditBindingModel bindingModel = this.modelMapper.map(viewModel, VideoEditBindingModel.class);
            bindingModel.setCategory(viewModel.getCategory().getName());
            model.addAttribute("videoInput", bindingModel);
        }
        modelAndView.addObject("categories", categoriesNames);

        return modelAndView;
    }

    @PostMapping("/edit/{identifier}")
    public ModelAndView editVideo(@PathVariable String identifier, @Valid @ModelAttribute(name = "videoInput") VideoEditBindingModel videoEditBindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
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
