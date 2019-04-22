package dst.courseproject.controllers;

import com.sun.security.auth.UserPrincipal;
import dst.courseproject.exceptions.VideoAlreadyLiked;
import dst.courseproject.exceptions.VideoNotLiked;
import dst.courseproject.services.VideoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VideoRestControllerTests {
    private VideoService videoService;
    private VideoRestController controller;

    @Before
    public void init() {
        this.videoService = mock(VideoService.class);
        this.controller = new VideoRestController(this.videoService);
    }

    @Test
    public void videoRestController_videoLikes_returnCorrect() {
        when(this.videoService.getVideoLikes("aBcDe")).thenReturn(15);
        Integer expected = 15;
        Integer actual = this.videoService.getVideoLikes("aBcDe");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void videoRestController_likeVideo_returnTrue() throws VideoAlreadyLiked {
        boolean expected = this.controller.likeVideo("aBcDe", new UserPrincipal("pesho@peshev.com"));
        Assert.assertTrue(expected);
    }

    @Test
    public void videoRestController_dislikeVideo_returnTrue() throws VideoNotLiked {
        boolean expected = this.controller.dislikeVideo("aBcDe", new UserPrincipal("pesho@peshev.com"));
        Assert.assertTrue(expected);
    }
}
