package dst.courseproject.tasks;

import dst.courseproject.services.LogService;
import dst.courseproject.services.UserService;
import dst.courseproject.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyUsersAndVideosCountInformationTask {
    private static final String USERS_MESSAGE = "Total users - %d, active users - %d.%n";
    private static final String VIDEOS_MESSAGE = "Total videos - %d.%n";

    private final UserService userService;
    private final VideoService videoService;
    private final LogService logService;

    @Autowired
    public DailyUsersAndVideosCountInformationTask(UserService userService, VideoService videoService, LogService logService) {
        this.userService = userService;
        this.videoService = videoService;
        this.logService = logService;
    }

//    @Scheduled(fixedRate = 10000)
    @Scheduled(cron = "22 22 22 * * *")
    public void write() {
        String logContent = String.format(USERS_MESSAGE, this.userService.getTotalUsersCount(),                              this.userService.getTotalActiveUsersCount()) +
                            String.format(VIDEOS_MESSAGE, this.videoService.getTotalActiveVideosCount());

        this.logService.addLog(logContent);
    }
}
