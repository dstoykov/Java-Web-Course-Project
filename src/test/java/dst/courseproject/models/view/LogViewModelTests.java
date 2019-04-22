package dst.courseproject.models.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class LogViewModelTests {
    private LogViewModel logViewModel;

    @Before
    public void init() {
        this.logViewModel = new LogViewModel();
    }

    @Test
    public void logViewModel_idFieldGetterAndSetter_returnCorrect() {
        this.logViewModel.setId("1");
        String expected = "1";
        String actual  = this.logViewModel.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void logViewModel_dateFieldGetterAndSetter_returnCorrect() {
        LocalDateTime dateTime = LocalDateTime.now();
        this.logViewModel.setDate(dateTime);
        LocalDateTime actual  = this.logViewModel.getDate();

        Assert.assertEquals(dateTime, actual);
    }

    @Test
    public void logViewModel_contentFieldGetterAndSetter_returnCorrect() {
        this.logViewModel.setContent("Content");
        String expected = "Content";
        String actual  = this.logViewModel.getContent();

        Assert.assertEquals(expected, actual);
    }
}
