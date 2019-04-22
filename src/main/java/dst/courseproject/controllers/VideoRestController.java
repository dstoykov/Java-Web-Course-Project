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
    public boolean likeVideo(@PathVariable String identifier, Principal principal) throws VideoAlreadyLiked {
        this.videoService.likeVideo(identifier, principal.getName());
        return true;
    }

    @PostMapping("/{identifier}/unlike")
    public boolean dislikeVideo(@PathVariable String identifier, Principal principal) throws VideoNotLiked {
        this.videoService.unlikeVideo(identifier, principal.getName());
        return true;
    }
}