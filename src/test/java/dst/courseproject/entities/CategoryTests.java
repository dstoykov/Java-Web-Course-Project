package dst.courseproject.entities;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class CategoryTests {
    private Category category = new Category();

    @Test
    public void category_idFieldGetterAndSetter_returnCorrect() {
        this.category.setId("123");
        String expected = "123";
        String actual = this.category.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void category_nameFieldGetterAndSetter_returnCorrect() {
        this.category.setName("Music");
        String expected = "Music";
        String actual = this.category.getName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void category_videosFieldGetterAndSetter_returnCorrect() {
        Set<Video> expected = new HashSet<>() {{
            new Video(){{
                setId("123");
                setTitle("Video1");
            }};
            new Video(){{
                setId("321");
                setTitle("Video2");
            }};
        }};
        this.category.setVideos(expected);
        Set<Video> actual = this.category.getVideos();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void category_deletedOnFieldGetterAndSetter_returnCorrect() {
        this.category.setDeletedOn(LocalDate.now());
        Assert.assertNotNull(this.category.getDeletedOn());
    }
}
