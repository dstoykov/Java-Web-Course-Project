package dst.courseproject.services;

import dst.courseproject.entities.Category;
import dst.courseproject.models.binding.AddCategoryBindingModel;
import dst.courseproject.models.binding.EditCategoryBindingModel;
import dst.courseproject.models.service.CategoryServiceModel;

import javax.validation.Valid;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    void addCategory(@Valid AddCategoryBindingModel categoryBindingModel);

    CategoryServiceModel getCategoryServiceModel(String id);

    EditCategoryBindingModel getBindingModelFromServiceModel(CategoryServiceModel categoryServiceModel);

    void editCategory(@Valid EditCategoryBindingModel editCategoryBindingModel, String id);

    void deleteCategory(String id);
}
