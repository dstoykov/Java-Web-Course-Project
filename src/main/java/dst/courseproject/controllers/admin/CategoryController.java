package dst.courseproject.controllers.admin;

import dst.courseproject.models.binding.AddCategoryBindingModel;
import dst.courseproject.models.binding.EditCategoryBindingModel;
import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
        System.out.println();
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

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id, ModelAndView modelAndView, Model model) {
        CategoryServiceModel categoryServiceModel = this.categoryService.getCategoryServiceModel(id);
        modelAndView.setViewName("category-edit");

        if (!model.containsAttribute("categoryInput")) {
            EditCategoryBindingModel editCategoryBindingModel = this.categoryService.getBindingModelFromServiceModel(categoryServiceModel);
            model.addAttribute("categoryInput", editCategoryBindingModel);
        }

        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id, @Valid @ModelAttribute(name = "categoryInput") EditCategoryBindingModel editCategoryBindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryInput", bindingResult);
            redirectAttributes.addFlashAttribute("categoryInput", editCategoryBindingModel);
            modelAndView.setViewName("redirect:/admin/categories/edit/" + id);
        } else {
            this.categoryService.editCategory(editCategoryBindingModel, id);
            modelAndView.setViewName("redirect:../all");
        }

        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(@PathVariable("id") String id, ModelAndView modelAndView, Model model) {
        CategoryServiceModel categoryServiceModel = this.categoryService.getCategoryServiceModel(id);
        modelAndView.setViewName("category-delete");

        if (!model.containsAttribute("categoryInput")) {
            EditCategoryBindingModel editCategoryBindingModel = this.categoryService.getBindingModelFromServiceModel(categoryServiceModel);
            model.addAttribute("categoryInput", editCategoryBindingModel);
        }

        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.categoryService.deleteCategory(id);
        modelAndView.setViewName("redirect:../all");

        return modelAndView;
    }
}
