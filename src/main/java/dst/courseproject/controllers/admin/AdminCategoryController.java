package dst.courseproject.controllers.admin;

import dst.courseproject.models.binding.CategoryAddBindingModel;
import dst.courseproject.models.binding.CategoryEditBindingModel;
import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.models.view.CategoryViewModel;
import dst.courseproject.services.CategoryService;
import org.modelmapper.ModelMapper;
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
public class AdminCategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminCategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ModelAndView allCategories(ModelAndView modelAndView) {
        modelAndView.setViewName("admin-categories-all");
        modelAndView.addObject("title", "All Categories");
        modelAndView.addObject("categories", this.categoryService.getAllCategories());

        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addCategory(ModelAndView modelAndView, Model model) {
        modelAndView.setViewName("admin-category-add");
        modelAndView.addObject("title", "Add Category");
        if (!model.containsAttribute("addCategoryInput")) {
            model.addAttribute("addCategoryInput", new CategoryAddBindingModel());
        }

        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addCategory(@Valid @ModelAttribute(name = "addCategoryInput") CategoryAddBindingModel addCategoryInput, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
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
        modelAndView.setViewName("admin-category-edit");
        modelAndView.addObject("title", "Edit Category");

        if (!model.containsAttribute("categoryInput")) {
            CategoryViewModel categoryViewModel = this.modelMapper.map(categoryServiceModel, CategoryViewModel.class);
            model.addAttribute("categoryInput", categoryViewModel);
        }

        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id, @Valid @ModelAttribute(name = "categoryInput") CategoryEditBindingModel categoryEditBindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.categoryInput", bindingResult);
            redirectAttributes.addFlashAttribute("categoryInput", categoryEditBindingModel);
            modelAndView.setViewName("redirect:/admin/categories/edit/" + id);
        } else {
            this.categoryService.editCategory(categoryEditBindingModel, id);
            modelAndView.setViewName("redirect:../all");
        }

        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete(@PathVariable("id") String id, ModelAndView modelAndView, Model model) {
        CategoryServiceModel categoryServiceModel = this.categoryService.getCategoryServiceModel(id);
        modelAndView.setViewName("admin-category-delete");
        modelAndView.addObject("title", "Delete Category");

        if (!model.containsAttribute("categoryInput")) {
            CategoryViewModel categoryViewModel = this.modelMapper.map(categoryServiceModel, CategoryViewModel.class);
            model.addAttribute("categoryInput", categoryViewModel);
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
