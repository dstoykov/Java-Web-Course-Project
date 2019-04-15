package dst.courseproject.services;

import dst.courseproject.entities.Category;
import dst.courseproject.models.binding.CategoryAddBindingModel;
import dst.courseproject.models.binding.CategoryEditBindingModel;
import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.models.view.CategoryViewModel;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

public interface CategoryService {
    List<Category> getAllCategories();

    void addCategory(@Valid CategoryAddBindingModel categoryBindingModel);

    CategoryServiceModel getCategoryServiceModel(String id);

    void editCategory(@Valid CategoryEditBindingModel categoryEditBindingModel, String id);

    void deleteCategory(String id);

    CategoryViewModel getCategoryViewModel(String name);

    Set<String> getCategoriesNames();

    CategoryServiceModel getCategoryServiceModelByName(String name);
}
