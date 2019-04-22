package dst.courseproject.services;

import dst.courseproject.entities.Category;
import dst.courseproject.models.binding.CategoryAddBindingModel;
import dst.courseproject.models.binding.CategoryEditBindingModel;
import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.models.view.CategoryViewModel;
import dst.courseproject.repositories.CategoryRepository;
import dst.courseproject.services.impl.CategoryServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoryServiceTests {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    private CategoryService categoryService;

    @Before
    public void init() {
        this.categoryRepository = mock(CategoryRepository.class);
        this.modelMapper = new ModelMapper();
        this.categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        Category category = new Category(){{
            setId("1");
            setName("Music");
        }};
        when(this.categoryRepository.findByIdAndDeletedOnNull("1")).thenReturn(category);
        when(this.categoryRepository.findAllByDeletedOnNull()).thenReturn(List.of(
                new Category(){{
                    setId("1");
                    setName("Music");
                }},
                new Category() {{
                    setId("2");
                    setName("Sport");
                }},
                new Category(){{
                    setId("3");
                    setName("Entertainment");
                }}
        ));
        when(this.categoryRepository.findByNameAndDeletedOnNull("Music")).thenReturn(category);
    }

    @Test
    public void categoryService_addCategory_returnServiceModel() {
        //Assert
        CategoryAddBindingModel model = new CategoryAddBindingModel(){{
            setName("Music");
        }};

        //Act
        CategoryServiceModel actual = this.categoryService.addCategory(model);

        //Assert
        Assert.assertEquals(model.getName(), actual.getName());
    }

    @Test
    public void categoryService_getServiceModelById_returnCorrect() {
        //Act
        CategoryServiceModel expected = new CategoryServiceModel(){{
            setId("1");
            setName("Music");
        }};
        CategoryServiceModel actual = this.categoryService.getCategoryServiceModel(expected.getId());

        //Assert
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void categoryService_editCategory_returnServiceModel() {
        when(this.categoryRepository.getOne("1")).thenReturn(new Category(){{
            setName("Music");
        }});
        //Arrange
        CategoryEditBindingModel model = new CategoryEditBindingModel(){{
            setName("Sport");
        }};

        //Act
        CategoryServiceModel actual = this.categoryService.editCategory(model, "1");

        //Assert
        Assert.assertEquals(model.getName(), actual.getName());
    }

    @Test
    public void categoryService_deleteCategory_returnServiceModel() {
        when(this.categoryRepository.getOne("1")).thenReturn(new Category(){{
            setName("Music");
        }});

        //Act
        CategoryServiceModel actual = this.categoryService.deleteCategory("1");

        //Assert
        Assert.assertEquals("Music", actual.getName());
    }

    @Test
    public void categoryService_getCategoriesAsViewModels_returnsCorrectCollection() {
        //Arrange
        List<CategoryViewModel> expected = new ArrayList<>(){{
            add(new CategoryViewModel(){{
                setId("1");
                setName("Music");
            }});
            add(new CategoryViewModel(){{
                setId("2");
                setName("Sport");
            }});
            add(new CategoryViewModel(){{
                setId("3");
                setName("Entertainment");
            }});
        }};

        //Act
        List<CategoryViewModel> actual = new ArrayList<>(this.categoryService.getAllCategoriesAsViewModels());

        //Assert
        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i).getId(), actual.get(i).getId());
            Assert.assertEquals(expected.get(i).getName(), actual.get(i).getName());
        }
    }

    @Test
    public void categoryService_getViewModelByName_returnCorrect() {
        //Arrange
        CategoryViewModel expected = new CategoryViewModel(){{
            setId("1");
            setName("Music");
        }};

        //Act
        CategoryViewModel actual = this.categoryService.getCategoryViewModel("Music");

        //Assert
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void categoryService_getCategoriesNames_returnCorrectNames() {
        //Arrange
        List<String> expected = new ArrayList<>(){{
            add("Sport");
            add("Entertainment");
            add("Music");
        }};

        //Act
        List<String> actual = new ArrayList<>(this.categoryService.getCategoriesNames());

        //Assert
        Assert.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void categoryService_getServiceModelByName_returnCorrect() {
        //Arrange
        CategoryServiceModel expected = new CategoryServiceModel(){{
            setId("1");
            setName("Music");
        }};

        //Act
        CategoryServiceModel actual = this.categoryService.getCategoryServiceModelByName(expected.getName());

        //Assert
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }
}