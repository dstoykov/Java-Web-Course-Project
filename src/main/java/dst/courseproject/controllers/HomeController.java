package dst.courseproject.controllers;

import dst.courseproject.models.view.VideoViewModel;
import dst.courseproject.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Base64;
import java.util.Set;

@Controller
public class HomeController {
    private final VideoService videoService;

    @Autowired
    public HomeController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        Set<VideoViewModel> mostPopular = this.videoService.get20MostPopularVideos();
        Set<VideoViewModel> latest = this.videoService.get20LatestVideos();

        modelAndView.setViewName("index");
        modelAndView.addObject("latest", latest);
        modelAndView.addObject("mostPopular", mostPopular);
        modelAndView.addObject("title", "Home");

        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam("query") String query, ModelAndView modelAndView) {
        Set<VideoViewModel> videos = this.videoService.getViewModelsForSearch(query);

        modelAndView.setViewName("search");
        modelAndView.addObject("title", "Results for " + query);
        modelAndView.addObject("query", query);
        modelAndView.addObject("videos", videos);
        modelAndView.addObject("encoder", Base64.getEncoder());

        return modelAndView;
    }
}