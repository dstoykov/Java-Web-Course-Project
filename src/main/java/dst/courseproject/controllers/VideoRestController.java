package dst.courseproject.controllers;

import dst.courseproject.exceptions.VideoAlreadyLiked;
import dst.courseproject.exceptions.VideoNotLiked;
import dst.courseproject.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/videos")
public class VideoRestController {
    private final VideoService videoService;

    @Autowired
    public VideoRestController(VideoService videoService) {
        this.videoService = videoService;
    }


    @GetMapping("/{identifier}/likes")
    public Integer videoLikes(@PathVariable String identifier) {
        return this.videoService.getVideoLikes(identifier);
    }

    @PostMapping("/{identifier}/like")
    public void likeVideo(@PathVariable String identifier, Principal principal) {
        try {
            this.videoService.likeVideo(identifier, principal.getName());
        } catch (VideoAlreadyLiked videoAlreadyLiked) {
            videoAlreadyLiked.printStackTrace();
        }
    }

    @PostMapping("/{identifier}/unlike")
    public void dislikeVideo(@PathVariable String identifier, Principal principal) {
        try {
            this.videoService.unlikeVideo(identifier, principal.getName());
        } catch (VideoNotLiked videoNotLiked) {
            videoNotLiked.printStackTrace();
        }
    }
}
