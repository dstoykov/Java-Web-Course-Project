package dst.courseproject.models.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceModelTests {
    private CategoryServiceModel categoryServiceModel;

    @Before
    public void init() {
        this.categoryServiceModel = new CategoryServiceModel();
    }

    @Test
    public void categoryViewModel_idFieldGetterAndSetter_returnCorrect() {
        this.categoryServiceModel.setId("1");
        String expected = "1";
        String actual = this.categoryServiceModel.getId();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void categoryViewModel_nameFieldGetterAndSetter_returnCorrect() {
        this.categoryServiceModel.setName("Name");
        String expected = "Name";
        String actual = this.categoryServiceModel.getName();

        Assert.assertEquals(expected, actual);
    }

}
