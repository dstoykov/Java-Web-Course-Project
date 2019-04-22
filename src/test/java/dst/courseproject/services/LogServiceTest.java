package dst.courseproject.services;

import dst.courseproject.entities.Log;
import dst.courseproject.models.view.LogViewModel;
import dst.courseproject.repositories.LogRepository;
import dst.courseproject.services.impl.LogServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class LogServiceTest {
    private LogRepository logRepository;
    private ModelMapper modelMapper;
    private LogService logService;

    @Before
    public void init() {
        this.logRepository = mock(LogRepository.class);
        this.modelMapper = new ModelMapper();
        this.logService = new LogServiceImpl(this.logRepository, this.modelMapper);
    }

    @Test
    public void logService_addLog_returnViewModel() {
        //Assert
        String expected = "Long log";

        //Act
        LogViewModel actual = this.logService.addLog(expected);

        //Arrange
        Assert.assertEquals(expected, actual.getContent());
    }

    @Test
    public void logService_getViewModels_returnCorrect() {
        //Arrange
        when(this.logRepository.getAllByIdNotNullOrderByDateDesc()).thenReturn(new LinkedHashSet<>(){{
            add(new Log(){{
                setContent("Log1");
            }});
            add(new Log(){{
                setContent("Log2");
            }});
            add(new Log(){{
                setContent("Log3");
            }});
        }});
        List<LogViewModel> expected = new ArrayList<>() {{
            add(new LogViewModel(){{
                setContent("Log1");
            }});
            add(new LogViewModel(){{
                setContent("Log2");
            }});
            add(new LogViewModel(){{
                setContent("Log3");
            }});
        }};

        //Act
        Set<LogViewModel> actual = this.logService.getLogViewModels();
        int i = 0;

        //Assert
        Assert.assertEquals(expected.size(), actual.size());
        for (LogViewModel actualLog : actual) {
            Assert.assertEquals(expected.get(i).getContent(), actualLog.getContent());
            i++;
        }
    }

    @Test
    public void logService_getViewModelById_returnCorrect() {
        //Arrange
        when(this.logRepository.getOne("1")).thenReturn(new Log(){{
            setId("1");
            setContent("Log1");
        }});
        LogViewModel expected = new LogViewModel(){{
            setId("1");
            setContent("Log1");
        }};

        //Act
        LogViewModel actual = this.logService.getViewModelById("1");

        //Assert
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getContent(), actual.getContent());
    }
}
