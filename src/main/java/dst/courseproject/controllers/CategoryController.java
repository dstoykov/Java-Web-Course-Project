package dst.courseproject.controllers;

import dst.courseproject.models.view.CategoryViewModel;
import dst.courseproject.services.CategoryService;
import dst.courseproject.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{name}")
    public ModelAndView categoryDetails(@PathVariable String name, ModelAndView modelAndView) {
        CategoryViewModel categoryViewModel = this.categoryService.getCategoryViewModel(name);
        modelAndView.setViewName("category-details");
        modelAndView.addObject("category", categoryViewModel);

        return modelAndView;
    }
}
