package dst.courseproject.controllers;

import dst.courseproject.models.service.CategoryServiceModel;
import dst.courseproject.models.view.CategoryViewModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.CategoryService;
import dst.courseproject.services.VideoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@SpringBootTest
@SuppressWarnings("unchecked")
public class CategoryControllerTests {
    private CategoryController controller;
    private CategoryService categoryService;
    private VideoService videoService;

    @Before
    public void init() {
        this.categoryService = mock(CategoryService.class);
        this.videoService = mock(VideoService.class);
        this.controller = new CategoryController(this.categoryService, this.videoService, new ModelMapper());
    }

    @Test
    public void categoryController_categoryDetailsGetRequest_returnCorrectModelAndView() {
        when(this.categoryService.getCategoryViewModel("Music")).thenReturn(new CategoryViewModel(){{
            setName("Music");
        }});
        when(this.videoService.getVideosByCategoryAsViewModel(new CategoryServiceModel(){{
            setName("Music");
        }})).thenReturn(new LinkedHashSet<>(){{
            add(new VideoViewModel(){{
                setTitle("Music1");
            }});
            add(new VideoViewModel(){{
                setTitle("Music2");
            }});
        }});
        ModelAndView modelAndView = new ModelAndView();
        this.controller.categoryDetails("Music", modelAndView);

        CategoryViewModel expectedCategory = new CategoryViewModel(){{setName("Music");}};
        CategoryViewModel actualCategory = (CategoryViewModel) modelAndView.getModelMap().get("category");
        List<VideoViewModel> expectedVideos = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setTitle("Music1");
            }});
            add(new VideoViewModel(){{
                setTitle("Music2");
            }});
        }};
        Set<VideoViewModel> actualVideos = (Set<VideoViewModel>) modelAndView.getModelMap().get("videos");
        int i = 0;

        Assert.assertEquals("category-details", modelAndView.getViewName());
        Assert.assertEquals(expectedCategory.getName(), actualCategory.getName());
        for (VideoViewModel actual : actualVideos) {
            Assert.assertEquals(expectedVideos.get(i++).getTitle(), actual.getTitle());
        }
    }
}