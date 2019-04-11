package dst.courseproject.tasks;

import dst.courseproject.services.UserService;
import dst.courseproject.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyUsersAndVideosCountInformationTask {
    private final UserService userService;
    private final VideoService videoService;

    @Autowired
    public DailyUsersAndVideosCountInformationTask(UserService userService, VideoService videoService) {
        this.userService = userService;
        this.videoService = videoService;
    }

    @Scheduled(fixedRate = 10000)
//    @Scheduled(cron = "22 22 22 * * *")
    public void write() {
        System.out.printf("Total users - %d, active users - %d.%n", this.userService.getTotalUsersCount(), this.userService.getTotalActiveUsersCount());
        System.out.printf("Total videos - %d.%n", this.videoService.getTotalActiveVideosCount());
    }
}
