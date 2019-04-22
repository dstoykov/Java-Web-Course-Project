package dst.courseproject.controllers.admin;

import dst.courseproject.models.binding.VideoEditBindingModel;
import dst.courseproject.models.view.CategoryViewModel;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.CategoryService;
import dst.courseproject.services.VideoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@SpringBootTest
@SuppressWarnings("unchecked")
public class AdminVideoControllerTests {
    private AdminVideoController controller;
    private VideoService videoService;
    private CategoryService categoryService;

    @Before
    public void init() {
        this.videoService = mock(VideoService.class);
        this.categoryService = mock(CategoryService.class);
        this.controller = new AdminVideoController(this.videoService, this.categoryService, new ModelMapper());
        when(this.videoService.getVideoViewModel("aBcDe")).thenReturn(new VideoViewModel(){{
            setId("1");
            setTitle("Video1");
            setVideoIdentifier("aBcDe");
            setCategory(new CategoryViewModel(){{
                setName("Music");
            }});
        }});
    }

    @Test
    public void adminVideoController_editVideoGetRequest_returnCorrectModelAndView() {
        when(this.categoryService.getCategoriesNames()).thenReturn(new LinkedHashSet<>(){{
            add("Music");
            add("Sport");
            add("Entertainment");
        }});
        ModelAndView modelAndView = new ModelAndView();
        Model model = new ConcurrentModel();
        this.controller.editVideo("aBcDe", modelAndView, model);

        VideoEditBindingModel expectedVideo = new VideoEditBindingModel(){{
            setTitle("Video1");
            setCategory("Music");
            setIdentifier("aBcDe");
        }};
        VideoEditBindingModel actualVideo = (VideoEditBindingModel) model.asMap().get("videoInput");
        List<String> expectedCategories = new ArrayList<>(){{
            add("Music");
            add("Sport");
            add("Entertainment");
        }};
        Set<String> actualCategories = (Set<String>) modelAndView.getModelMap().get("categories");
        int i = 0;

        Assert.assertEquals("video-edit", modelAndView.getViewName());
        Assert.assertEquals("Edit Video", modelAndView.getModelMap().get("title"));
        for (String actual : actualCategories) {
            Assert.assertEquals(expectedCategories.get(i++), actual);
        }
        Assert.assertEquals(expectedVideo.getTitle(), actualVideo.getTitle());
        Assert.assertEquals(expectedVideo.getCategory(), actualVideo.getCategory());
        Assert.assertEquals(expectedVideo.getIdentifier(), actualVideo.getIdentifier());
    }

    @Test
    public void adminVideoController_editVideoPostRequestWithError_returnCorrectModelAndView() {
        VideoEditBindingModel model = new VideoEditBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "videoInput");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        bindingResult.addError(new ObjectError("err", "err"));
        this.controller.editVideo("aBcDe", model, bindingResult, modelAndView, redirectAttributes);

        Assert.assertEquals(bindingResult, redirectAttributes.getFlashAttributes().get("org.springframework.validation.BindingResult.videoInput"));
        Assert.assertEquals(model, redirectAttributes.getFlashAttributes().get("videoInput"));
        Assert.assertEquals("redirect:aBcDe", modelAndView.getViewName());
    }

    @Test
    public void adminVideoController_editVideoPostRequestWithNoError_returnCorrectModelAndView() {
        VideoEditBindingModel model = new VideoEditBindingModel();
        ModelAndView modelAndView = new ModelAndView();
        BindingResult bindingResult = new BeanPropertyBindingResult(model, "videoInput");
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        this.controller.editVideo("aBcDe", model, bindingResult, modelAndView, redirectAttributes);

        Assert.assertEquals("redirect:../aBcDe", modelAndView.getViewName());
    }

    @Test
    public void adminVideoController_deleteVideoGetRequest_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        this.controller.deleteVideo("aBcDe", modelAndView);

        VideoViewModel expected = new VideoViewModel(){{
            setId("1");
            setTitle("Video1");
            setVideoIdentifier("aBcDe");
            setCategory(new CategoryViewModel(){{
                setName("Music");
            }});
        }};
        VideoViewModel actual = (VideoViewModel) modelAndView.getModelMap().get("videoInput");

        Assert.assertEquals("video-delete", modelAndView.getViewName());
        Assert.assertEquals("Delete Video", modelAndView.getModelMap().get("title"));
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getTitle(), actual.getTitle());
        Assert.assertEquals(expected.getVideoIdentifier(), actual.getVideoIdentifier());
        Assert.assertEquals(expected.getCategory().getName(), actual.getCategory().getName());
    }

    @Test
    public void adminVideoController_deleteVideoPostRequest_returnCorrectModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        this.controller.deleteVideoConfirm("aBcDe", modelAndView);

        Assert.assertEquals("redirect:../../", modelAndView.getViewName());
    }
}