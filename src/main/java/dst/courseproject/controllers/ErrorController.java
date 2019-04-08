package dst.courseproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/errors")
public class ErrorController {
    @GetMapping("/error")
    public ModelAndView error(ModelAndView modelAndView, Principal principal) {
        modelAndView.setViewName("error/default-error-page");
        modelAndView.addObject("title", "Oops...");
        System.out.println(principal);

        return modelAndView;
    }
}
