package dst.courseproject.services;

import dst.courseproject.entities.Category;
import dst.courseproject.models.binding.AddCategoryBindingModel;

import javax.validation.Valid;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    void addCategory(@Valid AddCategoryBindingModel categoryBindingModel);
}
