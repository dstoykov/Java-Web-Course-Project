package dst.courseproject.controllers;

import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
public class HomeController {
    private final VideoService videoService;

    @Autowired
    public HomeController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/")
    private ModelAndView index(ModelAndView modelAndView) {
        Set<VideoViewModel> videos = this.videoService.getAllVideosAsViewModels();

        modelAndView.setViewName("index");
        modelAndView.addObject("videos", videos);
        modelAndView.addObject("title", "In the Box");

        return modelAndView;
    }
}