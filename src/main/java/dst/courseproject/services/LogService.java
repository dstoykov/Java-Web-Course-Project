package dst.courseproject.services;

import dst.courseproject.models.view.LogViewModel;

import java.util.Set;

public interface LogService {
    void addLog(String content);

    Set<LogViewModel> getLogViewModels();

    LogViewModel getViewModelById(String id);
}
