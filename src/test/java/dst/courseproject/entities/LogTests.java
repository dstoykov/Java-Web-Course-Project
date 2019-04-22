package dst.courseproject.entities;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class LogTests {
    private Log log = new Log();

    @Test
    public void log_idFieldGetterAndSetter_returnCorrect() {
        this.log.setId("123");
        String expected = "123";
        String actual = this.log.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void log_dateFieldGetterAndSetter_returnCorrect() {
        LocalDateTime expected = LocalDateTime.now();
        this.log.setDate(expected);
        LocalDateTime actual = this.log.getDate();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void log_contentFieldGetterAndSetter_returnCorrect() {
        this.log.setContent("Content");
        String expected = "Content";
        String actual = this.log.getContent();

        Assert.assertEquals(expected, actual);
    }
}
