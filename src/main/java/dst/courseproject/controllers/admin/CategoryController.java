package dst.courseproject.controllers.admin;

import dst.courseproject.models.binding.AddCategoryBindingModel;
import dst.courseproject.services.CategoryService;
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

@Controller
@RequestMapping("/admin/categories")
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
    public ModelAndView addCategory(ModelAndView modelAndView, Model model) {
        modelAndView.setViewName("category-add");
        if (!model.containsAttribute("addCategoryInput")) {
            model.addAttribute("addCategoryInput", new AddCategoryBindingModel());
        }

        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addCategory(@Valid @ModelAttribute(name = "addCategoryInput") AddCategoryBindingModel addCategoryInput, ModelAndView modelAndView, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCategoryInput", bindingResult);
            redirectAttributes.addFlashAttribute("addCategoryInput", addCategoryInput);
            modelAndView.addObject("redirect:add");
        } else {
            this.categoryService.addCategory(addCategoryInput);
            modelAndView.setViewName("redirect:all");
        }

        return modelAndView;
    }
}
