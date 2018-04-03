package dst.courseproject.services.impl;

import dst.courseproject.entities.Category;
import dst.courseproject.models.binding.AddCategoryBindingModel;
import dst.courseproject.repositories.CategoryRepository;
import dst.courseproject.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
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
}
