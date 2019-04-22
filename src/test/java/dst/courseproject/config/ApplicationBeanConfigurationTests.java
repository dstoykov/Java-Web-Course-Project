package dst.courseproject.config;

import org.junit.Assert;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class ApplicationBeanConfigurationTests {
    private ApplicationBeanConfiguration applicationBeanConfiguration = new ApplicationBeanConfiguration();

    @Test
    public void applicationBeanConfiguration_modelMapper_returnModelMapper() {
        Assert.assertEquals(ModelMapper.class, this.applicationBeanConfiguration.modelMapper().getClass());
    }

    @Test
    public void applicationBeanConfiguration_modelMapper_returnBCryptPasswordEncoder() {
        Assert.assertEquals(BCryptPasswordEncoder.class, this.applicationBeanConfiguration.encoder().getClass());
    }

    @Test
    public void applicationBeanConfiguration_modelMapper_returnRestTemplate() {
        Assert.assertEquals(RestTemplate.class, this.applicationBeanConfiguration.restTemplate().getClass());
    }
}
