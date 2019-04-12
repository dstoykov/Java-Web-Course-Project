package dst.courseproject.services.impl;

import dst.courseproject.entities.Log;
import dst.courseproject.models.view.LogViewModel;
import dst.courseproject.repositories.LogRepository;
import dst.courseproject.services.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addLog(String content) {
        Log log = new Log();
        log.setDate(LocalDateTime.now());
        log.setContent(content);

        this.logRepository.save(log);
    }

    @Override
    public Set<LogViewModel> getLogViewModels() {
        Set<Log> logs = this.logRepository.getAllByIdNotNullOrderByDateDesc();
        Set<LogViewModel> logViewModels = new LinkedHashSet<>();
        for (Log log : logs) {
            logViewModels.add(this.modelMapper.map(log, LogViewModel.class));
        }

        return logViewModels;
    }

    @Override
    public LogViewModel getViewModelById(String id) {
        LogViewModel logViewModel = this.modelMapper.map(this.logRepository.getOne(id), LogViewModel.class);
        return logViewModel;
    }
}
