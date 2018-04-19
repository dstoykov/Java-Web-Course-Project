package dst.courseproject.controllers;

import dst.courseproject.entities.Category;
import dst.courseproject.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/videos")
public class VideoController {
    private final CategoryService categoryService;

    @Autowired
    public VideoController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView modelAndView) {
        modelAndView.setViewName("add-video");
        modelAndView.addObject("title", "Add Video");
        List<Category> categories = this.categoryService.getAllCategories();
        List<String> categoriesNames = new ArrayList<>();
        for (Category category : categories) {
            categoriesNames.add(category.getName());
        }
        modelAndView.addObject("categories", categoriesNames);

        return modelAndView;
    }
}
