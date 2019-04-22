package dst.courseproject.controllers.admin;

import dst.courseproject.models.view.LogViewModel;
import dst.courseproject.services.LogService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminLogControllerTests {
    private LogService logService;
    private AdminLogController controller;

    @Before
    public void init() {
        this.logService = mock(LogService.class);
        this.controller = new AdminLogController(this.logService);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void adminLogController_allLogsGetRequest_returnCorrectModelAndView() {
        when(this.logService.getLogViewModels()).thenReturn(new LinkedHashSet<>(){{
            add(new LogViewModel(){{
                setId("1");
            }});
            add(new LogViewModel(){{
                setId("2");
            }});
        }});
        List<LogViewModel> expected = new ArrayList<>(){{
            add(new LogViewModel(){{
                setId("1");
            }});
            add(new LogViewModel(){{
                setId("2");
            }});
        }};
        ModelAndView modelAndView = new ModelAndView();
        this.controller.allLogs(modelAndView);
        Set<LogViewModel> actual = (Set<LogViewModel>) modelAndView.getModelMap().get("logs");
        int i = 0;

        Assert.assertEquals("admin-logs-all", modelAndView.getViewName());
        Assert.assertEquals("All Logs", modelAndView.getModelMap().get("title"));
        for (LogViewModel act : actual) {
            Assert.assertEquals(expected.get(i++).getId(), act.getId());
        }
    }

    @Test
    public void adminLogController_logDetailsGetRequest_returnCorrectModelAndView() {
        when(this.logService.getViewModelById("1")).thenReturn(new LogViewModel(){{
            setId("1");
            setContent("Long content.");
        }});
        ModelAndView modelAndView = new ModelAndView();
        this.controller.logDetails("1", modelAndView);
        LogViewModel expected = new LogViewModel(){{
            setId("1");
            setContent("Long content.");
        }};
        LogViewModel actual = (LogViewModel) modelAndView.getModelMap().get("log");

        Assert.assertEquals("admin-log-details", modelAndView.getViewName());
        Assert.assertEquals("Log Details", modelAndView.getModelMap().get("title"));
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getContent(), actual.getContent());
    }
}
