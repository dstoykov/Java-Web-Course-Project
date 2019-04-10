package dst.courseproject.controllers;

import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.models.view.CategoryViewModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.CategoryService;
import dst.courseproject.services.VideoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final VideoService videoService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, VideoService videoService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.videoService = videoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{name}")
    public ModelAndView categoryDetails(@PathVariable String name, ModelAndView modelAndView) {
        CategoryViewModel categoryViewModel = this.categoryService.getCategoryViewModel(name);
        Set<VideoViewModel> videoViewModels = this.videoService.getVideosByCategoryAsViewModel(this.modelMapper.map(categoryViewModel, CategoryServiceModel.class));

        modelAndView.setViewName("category-details");
        modelAndView.addObject("category", categoryViewModel);
        modelAndView.addObject("videos", videoViewModels);

        return modelAndView;
    }
}
