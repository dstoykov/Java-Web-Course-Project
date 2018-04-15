package dst.courseproject.services.impl;

import dst.courseproject.entities.Category;
import dst.courseproject.models.binding.AddCategoryBindingModel;
import dst.courseproject.models.binding.EditCategoryBindingModel;
import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.repositories.CategoryRepository;
import dst.courseproject.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public void addCategory(@Valid AddCategoryBindingModel categoryBindingModel) {
        Category category = this.modelMapper.map(categoryBindingModel, Category.class);
        this.categoryRepository.save(category);
    }

    @Override
    public CategoryServiceModel getCategoryServiceModel(String id) {
        Category category = this.categoryRepository.getOne(id);
        CategoryServiceModel categoryServiceModel = this.modelMapper.map(category, CategoryServiceModel.class);

        return categoryServiceModel;
    }

    @Override
    public EditCategoryBindingModel getBindingModelFromServiceModel(CategoryServiceModel categoryServiceModel) {
        return this.modelMapper.map(categoryServiceModel, EditCategoryBindingModel.class);
    }

    @Override
    public void editCategory(@Valid EditCategoryBindingModel editCategoryBindingModel, String id) {
        Category category = this.categoryRepository.getOne(id);
        category.setName(editCategoryBindingModel.getName());

        this.categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String id) {
        Category category = this.categoryRepository.getOne(id);
        this.categoryRepository.delete(category);
    }
}
