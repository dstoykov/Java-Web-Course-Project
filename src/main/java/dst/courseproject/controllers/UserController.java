package dst.courseproject.controllers;

import dst.courseproject.models.binding.RegisterUserBindingModel;
import dst.courseproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView, Model model) {
        modelAndView.setViewName("register");
        if (!model.containsAttribute("registerInput")) {
            model.addAttribute("registerInput", new RegisterUserBindingModel());
        }

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute(name = "registerInput") RegisterUserBindingModel userBindingModel, BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerInput", bindingResult);
            redirectAttributes.addFlashAttribute("registerInput", userBindingModel);
            modelAndView.setViewName("redirect:register");
        } else if (!userBindingModel.getPassword().equals(userBindingModel.getConfirmPassword())) {
            modelAndView.setViewName("redirect:register");
        } else {
            this.userService.register(userBindingModel);
            modelAndView.setViewName("redirect:login");
        }
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/logout")
    public ModelAndView logout(@RequestParam(required = false, name = "logout") String logout, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        modelAndView.setViewName("redirect:/login");

        if(logout != null) redirectAttributes.addFlashAttribute("logout", logout);

        return modelAndView;
    }
}
