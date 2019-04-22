package dst.courseproject.controllers;

import dst.courseproject.entities.Video;
import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.VideoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@SuppressWarnings("unchecked")
public class HomeControllerTests {
    private VideoService videoService;
    private HomeController homeController;

    @Before
    public void init() {
        this.videoService = mock(VideoService.class);
        this.homeController = new HomeController(this.videoService);
    }

    @Test
    public void homeController_index_returnCorrectModelAndView() {
        LocalDateTime first = LocalDateTime.now();
        LocalDateTime second = LocalDateTime.now();
        when(this.videoService.get20LatestVideos()).thenReturn(new LinkedHashSet<>(){{
            add(new VideoViewModel(){{
                setTitle("Video1");
                setUploadedOn(second);
            }});
            add(new VideoViewModel(){{
                setTitle("Video2");
                setUploadedOn(first);
            }});
        }});
        when(this.videoService.get20MostPopularVideos()).thenReturn(new LinkedHashSet<>(){{
            add(new VideoViewModel(){{
                setTitle("Video3");
                setViews(20L);
            }});
            add(new VideoViewModel(){{
                setTitle("Video3");
                setViews(10L);
            }});
        }});
        ModelAndView modelAndView = new ModelAndView();
        this.homeController.index(modelAndView);

        List<VideoViewModel> expectedLatest = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setTitle("Video1");
                setUploadedOn(second);
            }});
            add(new VideoViewModel(){{
                setTitle("Video2");
                setUploadedOn(first);
            }});
        }};
        List<VideoViewModel> expectedPopular = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setTitle("Video3");
                setViews(20L);
            }});
            add(new VideoViewModel(){{
                setTitle("Video3");
                setViews(10L);
            }});
        }};
        Set<VideoViewModel> actualLatest = (Set<VideoViewModel>) modelAndView.getModelMap().get("latest");
        Set<VideoViewModel> actualPopular = (Set<VideoViewModel>) modelAndView.getModelMap().get("mostPopular");
        int i = 0;

        for (VideoViewModel actual : actualLatest) {
            Assert.assertEquals(expectedLatest.get(i).getTitle(), actual.getTitle());
            Assert.assertEquals(expectedLatest.get(i++).getUploadedOn(), actual.getUploadedOn());
        }

        i = 0;
        for (VideoViewModel actual : actualPopular) {
            Assert.assertEquals(expectedPopular.get(i).getTitle(), actual.getTitle());
            Assert.assertEquals(expectedPopular.get(i++).getUploadedOn(), actual.getUploadedOn());
        }

        Assert.assertEquals("index", modelAndView.getViewName());
        Assert.assertEquals("Home", modelAndView.getModelMap().get("title"));
    }

    @Test
    public void homeController_search_returnCorrectModelAndView() {
        when(this.videoService.getViewModelsForSearch("berba")).thenReturn(new LinkedHashSet<>(){{
            add(new VideoViewModel(){{
                setTitle("Berbatov is the Best!");
            }});
            add(new VideoViewModel(){{
                setTitle("Top Berba's goals");
            }});
        }});
        ModelAndView modelAndView = new ModelAndView();
        this.homeController.search("berba", modelAndView);
        List<VideoViewModel> expected = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setTitle("Berbatov is the Best!");
            }});
            add(new VideoViewModel(){{
                setTitle("Top Berba's goals");
            }});
        }};
        Set<VideoViewModel> actual = (Set<VideoViewModel>) modelAndView.getModelMap().get("videos");
        int i = 0;

        Assert.assertEquals("Results for berba", modelAndView.getModelMap().get("title"));
        Assert.assertEquals("berba", modelAndView.getModelMap().get("query"));
        Assert.assertEquals(Base64.getEncoder(), modelAndView.getModelMap().get("encoder"));
        Assert.assertEquals("search", modelAndView.getViewName());
        for (VideoViewModel act : actual) {
            Assert.assertEquals(expected.get(i++).getTitle(), act.getTitle());
        }
    }
}
