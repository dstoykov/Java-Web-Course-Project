package dst.courseproject.controllers;

import dst.courseproject.models.binding.AddCategoryBindingModel;
import dst.courseproject.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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

    @GetMapping("/add")
    public ModelAndView addCategory(ModelAndView modelAndView) {
        modelAndView.setViewName("add-category");

        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addCategory(@Valid @ModelAttribute(name = "addCategoryInput") AddCategoryBindingModel categoryBindingModel, ModelAndView modelAndView, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCategoryInput", bindingResult);
            redirectAttributes.addFlashAttribute("addCategoryInput", categoryBindingModel);
            modelAndView.addObject("redirect:add");
        } else {
            this.categoryService.addCategory(categoryBindingModel);
            modelAndView.setViewName("redirect:all");
        }

        return modelAndView;
    }
}