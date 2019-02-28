package dst.courseproject.services.impl;

import dst.courseproject.entities.Category;
import dst.courseproject.models.binding.CategoryAddBindingModel;
import dst.courseproject.models.binding.CategoryEditBindingModel;
import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.models.view.CategoryViewModel;
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
    public void addCategory(@Valid CategoryAddBindingModel categoryBindingModel) {
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
    public CategoryEditBindingModel getBindingModelFromServiceModel(CategoryServiceModel categoryServiceModel) {
        return this.modelMapper.map(categoryServiceModel, CategoryEditBindingModel.class);
    }

    @Override
    public void editCategory(@Valid CategoryEditBindingModel categoryEditBindingModel, String id) {
        Category category = this.categoryRepository.getOne(id);
        category.setName(categoryEditBindingModel.getName());

        this.categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String id) {
        Category category = this.categoryRepository.getOne(id);
        this.categoryRepository.delete(category);
    }

    @Override
    public Category findByName(String categoryName) {
        Category category = this.categoryRepository.findByName(categoryName);
        return category;
    }

    @Override
    public CategoryViewModel getCategoryViewModel(String name) {
        Category category = this.categoryRepository.findByName(name);
        CategoryViewModel viewModel = this.modelMapper.map(category, CategoryViewModel.class);

        return viewModel;
    }
}
