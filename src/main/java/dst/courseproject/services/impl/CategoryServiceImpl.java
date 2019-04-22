package dst.courseproject.services.impl;

import dst.courseproject.entities.Category;
import dst.courseproject.entities.Video;
import dst.courseproject.models.binding.CategoryAddBindingModel;
import dst.courseproject.models.binding.CategoryEditBindingModel;
import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.models.view.CategoryViewModel;
import dst.courseproject.repositories.CategoryRepository;
import dst.courseproject.services.CategoryService;
import dst.courseproject.services.VideoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    public CategoryServiceModel addCategory(@Valid CategoryAddBindingModel categoryBindingModel) {
        Category category = this.modelMapper.map(categoryBindingModel, Category.class);
        this.categoryRepository.save(category);

        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public Set<CategoryViewModel> getAllCategoriesAsViewModels() {
        List<Category> categories = this.categoryRepository.findAllByDeletedOnNull();
        Set<CategoryViewModel> viewModels = new LinkedHashSet<>();
        for (Category category : categories) {
            viewModels.add(this.modelMapper.map(category, CategoryViewModel.class));
        }
        return viewModels;
    }

    @Override
    public CategoryServiceModel getCategoryServiceModel(String id) {
        Category category = this.categoryRepository.findByIdAndDeletedOnNull(id);
        CategoryServiceModel categoryServiceModel = this.modelMapper.map(category, CategoryServiceModel.class);

        return categoryServiceModel;
    }

    @Override
    public CategoryServiceModel editCategory(@Valid CategoryEditBindingModel categoryEditBindingModel, String id) {
        Category category = this.categoryRepository.findByIdAndDeletedOnNull(id);
        category.setName(categoryEditBindingModel.getName());

        this.categoryRepository.save(category);
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel deleteCategory(String id) {
        Category category = this.categoryRepository.findByIdAndDeletedOnNull(id);
        category.setDeletedOn(LocalDate.now());
        for (Video video : category.getVideos()) {
            video.setDeletedOn(LocalDate.now());
        }

        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public CategoryViewModel getCategoryViewModel(String name) {
        Category category = this.categoryRepository.findByNameAndDeletedOnNull(name);
        CategoryViewModel viewModel = this.modelMapper.map(category, CategoryViewModel.class);

        return viewModel;
    }

    @Override
    public Set<String> getCategoriesNames() {
        List<Category> categories = this.categoryRepository.findAllByDeletedOnNull();
        Set<String> names = new HashSet<>();
        for (Category category : categories) {
            names.add(category.getName());
        }

        return names;
    }

    @Override
    public CategoryServiceModel getCategoryServiceModelByName(String name) {
        Category category = this.categoryRepository.findByNameAndDeletedOnNull(name);
        CategoryServiceModel categoryServiceModel = this.modelMapper.map(category, CategoryServiceModel.class);

        return categoryServiceModel;
    }
}
