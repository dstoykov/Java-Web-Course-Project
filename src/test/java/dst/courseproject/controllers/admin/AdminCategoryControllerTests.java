package dst.courseproject.controllers.admin;

import dst.courseproject.models.binding.CategoryAddBindingModel;
import dst.courseproject.models.binding.CategoryEditBindingModel;
import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.models.view.CategoryViewModel;
import dst.courseproject.services.CategoryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminCategoryControllerTests {
    private CategoryService categoryService;
    private AdminCategoryController controller;

    @Before
    public void init() {
        this.categoryService = mock(CategoryService.class);
        this.controller = new AdminCategoryController(this.categoryService, new ModelMapper());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void adminCategoryController_allCategoriesGetRequest_returnCorrectModelAndView() {
        when(this.categoryService.getAllCategoriesAsViewModels()).thenReturn(new LinkedHashSet<>() {{
            add(new CategoryViewModel() {{
                setName("Music");
            }});
            add(new CategoryViewModel() {{
                setName("Sport");
            }});
        }});

        ModelAndView modelAndView = new ModelAndView();
        this.controller.allCategories(modelAndView);

        List<CategoryViewModel> expectedCategories = new ArrayList<>() {{
            add(new CategoryViewModel() {{
                setName("Music");
            }});
            add(new CategoryViewModel() {{
                setName("Sport");
            }});
        }};
        Set<CategoryViewModel> actualCategories = (Set<CategoryViewModel>) modelAndView.getModelMap().get("categories");

        Assert.assertEquals("admin-categories-all", modelAndView.getViewName());
        Assert.assertEquals("All Categories", modelAndView.getModelMap().get("title"));
        int i = 0;

        for (CategoryViewModel actual : actualCategories) {
            Assert.assertEquals(expectedCategories.get(i++).getName(), actual.getName());
        }
    }

    @Test
    public void adminCategoryController_addCategoryGetRequest_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        this.controller.addCategory(modelAndView, model);

        Assert.assertEquals("admin-category-add", modelAndView.getViewName());
        Assert.assertEquals("Add Category", modelAndView.getModelMap().get("title"));
        Assert.assertEquals(CategoryAddBindingModel.class, model.asMap().get("addCategoryInput").getClass());
    }

    @Test
    public void adminCategoryController_addCategoryPostRequestWithError_returnCorrectModelAndView() {
        CategoryAddBindingModel model = new CategoryAddBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "addCategoryInput");
        bindingResult.addError(new ObjectError("Test Error", "error"));
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.addCategory(model, bindingResult, modelAndView, redirectAttributes);

        Assert.assertTrue(bindingResult.hasErrors());
        Assert.assertEquals(bindingResult, redirectAttributes.getFlashAttributes().get("org.springframework.validation.BindingResult.addCategoryInput"));
        Assert.assertEquals(model, redirectAttributes.getFlashAttributes().get("addCategoryInput"));
        Assert.assertEquals("redirect:add", modelAndView.getViewName());
    }

    @Test
    public void adminCategoryController_addCategoryPostRequestWithNoErrors_returnCorrectModelAndView() {
        CategoryAddBindingModel model = new CategoryAddBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "addCategoryInput");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.addCategory(model, bindingResult, modelAndView, redirectAttributes);

        Assert.assertFalse(bindingResult.hasErrors());
        Assert.assertEquals(0, redirectAttributes.getFlashAttributes().size());
        Assert.assertEquals("redirect:all", modelAndView.getViewName());
    }

    @Test
    public void adminCategoryController_editCategoryGetRequest_returnCorrectModelAndView() {
        when(this.categoryService.getCategoryServiceModel("1")).thenReturn(new CategoryServiceModel(){{
            setId("1");
            setName("Music");
        }});
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        CategoryServiceModel expected = new CategoryServiceModel(){{
            setId("1");
            setName("Music");
        }};
        this.controller.edit("1", modelAndView, model);
        CategoryViewModel actual = (CategoryViewModel) model.asMap().get("categoryInput");

        Assert.assertEquals("admin-category-edit", modelAndView.getViewName());
        Assert.assertEquals("Edit Category", modelAndView.getModelMap().get("title"));
        Assert.assertEquals(CategoryViewModel.class, model.asMap().get("categoryInput").getClass());
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void adminCategoryController_editCategoryPostRequestWithError_returnCorrectModelAndView() {
        CategoryEditBindingModel model = new CategoryEditBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "addCategoryInput");
        bindingResult.addError(new ObjectError("Test Error", "error"));
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.edit("1", model, bindingResult, modelAndView, redirectAttributes);

        Assert.assertTrue(bindingResult.hasErrors());
        Assert.assertEquals(bindingResult, redirectAttributes.getFlashAttributes().get("org.springframework.validation.BindingResult.categoryInput"));
        Assert.assertEquals(model, redirectAttributes.getFlashAttributes().get("categoryInput"));
        Assert.assertEquals("redirect:/admin/categories/edit/1", modelAndView.getViewName());
    }

    @Test
    public void adminCategoryController_editCategoryPostRequestWithNoErrors_returnCorrectModelAndView() {
        CategoryEditBindingModel model = new CategoryEditBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "addCategoryInput");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.edit("1", model, bindingResult, modelAndView, redirectAttributes);

        Assert.assertFalse(bindingResult.hasErrors());
        Assert.assertEquals(0, redirectAttributes.getFlashAttributes().size());
        Assert.assertEquals("redirect:../all", modelAndView.getViewName());
    }

    @Test
    public void adminCategoryController_deleteCategoryGetRequest_returnCorrectModelAndView() {
        when(this.categoryService.getCategoryServiceModel("1")).thenReturn(new CategoryServiceModel(){{
            setId("1");
            setName("Music");
        }});
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        this.controller.delete("1", modelAndView, model);
        CategoryViewModel expected = new CategoryViewModel(){{
            setId("1");
            setName("Music");
        }};
        CategoryViewModel actual = (CategoryViewModel) model.asMap().get("categoryInput");

        Assert.assertEquals("admin-category-delete", modelAndView.getViewName());
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void adminCategoryController_deleteCategoryPostRequestWithNoErrors_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        this.controller.delete("1", modelAndView);

        Assert.assertEquals("redirect:../all", modelAndView.getViewName());
    }
}