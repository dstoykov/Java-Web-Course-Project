package dst.courseproject.models.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class CategoryViewModelTests {
    private CategoryViewModel categoryViewModel;

    @Before
    public void init() {
        this.categoryViewModel = new CategoryViewModel();
    }

    @Test
    public void categoryViewModel_idFieldGetterAndSetter_returnCorrect() {
        this.categoryViewModel.setId("1");
        String expected = "1";
        String actual = this.categoryViewModel.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void categoryViewModel_nameFieldGetterAndSetter_returnCorrect() {
        this.categoryViewModel.setName("Name");
        String expected = "Name";
        String actual = this.categoryViewModel.getName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void categoryViewModel_videosFieldGetterAndSetter_returnCorrect() {
        this.categoryViewModel.setVideos(new LinkedHashSet<>(){{
            add(new VideoViewModel(){{
                setTitle("Video1");
            }});
            add(new VideoViewModel(){{
                setTitle("Video2");
            }});
            add(new VideoViewModel(){{
                setTitle("Video3");
            }});
        }});
        List<VideoViewModel> expected = new ArrayList<>(){{
            add(new VideoViewModel(){{
                setTitle("Video1");
            }});
            add(new VideoViewModel(){{
                setTitle("Video2");
            }});
            add(new VideoViewModel(){{
                setTitle("Video3");
            }});
        }};
        Set<VideoViewModel> actual = this.categoryViewModel.getVideos();
        int i = 0;

        Assert.assertEquals(expected.size(), actual.size());
        for (VideoViewModel act : actual) {
            Assert.assertEquals(expected.get(i++).getTitle(), act.getTitle());
        }
    }
}