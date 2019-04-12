package dst.courseproject.controllers.admin;

import dst.courseproject.models.view.LogViewModel;
import dst.courseproject.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/admin/logs")
public class AdminLogController {
    private final LogService logService;

    @Autowired
    public AdminLogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/all")
    public ModelAndView allLogs(ModelAndView modelAndView) {
        Set<LogViewModel> logViewModels = this.logService.getLogViewModels();

        modelAndView.setViewName("admin-logs-all");
        modelAndView.addObject("title", "All Logs");
        modelAndView.addObject("logs", logViewModels);

        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView logDetails(@PathVariable("id") String id, ModelAndView modelAndView) {
        LogViewModel logViewModel = this.logService.getViewModelById(id);

        modelAndView.setViewName("admin-log-details");
        modelAndView.addObject("title", "Log Details");
        modelAndView.addObject("log", logViewModel);

        return modelAndView;
    }
}
