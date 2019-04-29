package dst.courseproject.services;

import dst.courseproject.exceptions.CategoryAlreadyExistsException;
import dst.courseproject.models.binding.CategoryAddBindingModel;
import dst.courseproject.models.binding.CategoryEditBindingModel;
import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.models.view.CategoryViewModel;

import javax.validation.Valid;
import java.util.Set;

public interface CategoryService {
    Set<CategoryViewModel> getAllCategoriesAsViewModels();

    CategoryServiceModel addCategory(@Valid CategoryAddBindingModel categoryBindingModel) throws CategoryAlreadyExistsException;

    CategoryServiceModel getCategoryServiceModel(String id);

    CategoryServiceModel editCategory(@Valid CategoryEditBindingModel categoryEditBindingModel, String id);

    CategoryServiceModel deleteCategory(String id);

    CategoryViewModel getCategoryViewModel(String name);

    Set<String> getCategoriesNames();

    CategoryServiceModel getCategoryServiceModelByName(String name);
}
