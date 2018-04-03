package dst.courseproject.controllers;

import dst.courseproject.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ModelAndView allCategories(ModelAndView modelAndView) {
        modelAndView.setViewName("categories-all");
        modelAndView.addObject("categories", this.categoryService.getAllCategories());

        return modelAndView;
    }


}
