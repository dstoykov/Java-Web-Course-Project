package dst.courseproject.config;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootTest
public class ThreadPoolTaskSchedulerConfigTests {
    private ThreadPoolTaskSchedulerConfig config = new ThreadPoolTaskSchedulerConfig();

    @Test
    public void threadPoolTaskSchedulerConfig_threadPoolTaskScheduler_returnCorrectThreadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = this.config.threadPoolTaskScheduler();
        int expectedPoolSize = 5;
        int actualPoolSize = threadPoolTaskScheduler.getPoolSize();
        String expectedNamePrefix = "ThreadPoolTaskScheduler_";
        String actualNamePrefix = threadPoolTaskScheduler.getThreadNamePrefix();

        Assert.assertEquals(expectedPoolSize, actualPoolSize);
        Assert.assertEquals(expectedNamePrefix, actualNamePrefix);
    }
}
