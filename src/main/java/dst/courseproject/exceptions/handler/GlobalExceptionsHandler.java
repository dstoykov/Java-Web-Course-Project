package dst.courseproject.exceptions.handler;

import com.dropbox.core.DbxException;
import dst.courseproject.exceptions.VideoAlreadyLiked;
import dst.courseproject.exceptions.VideoNotLiked;
import dst.courseproject.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionsHandler {
    private static final String REDIRECT_ERROR = "redirect:../errors/error";
    private static final String REDIRECT_INDEX = "redirect:../";
    private static final String REDIRECT_LOGIN = "redirect:../users/login";

    private final LogService logService;

    @Autowired
    public GlobalExceptionsHandler(LogService logService) {
        this.logService = logService;
    }

    @ExceptionHandler({DbxException.class, IOException.class, VideoAlreadyLiked.class, VideoNotLiked.class})
    public ModelAndView handleUploadingVideoExceptions(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(REDIRECT_ERROR);

        return modelAndView;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(REDIRECT_INDEX);

        return modelAndView;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDenied(AccessDeniedException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(REDIRECT_LOGIN);

        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAnyException(Exception e) {
        e.printStackTrace();
        this.logService.addLog(Arrays.toString(e.getStackTrace()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(REDIRECT_ERROR);

        return modelAndView;
    }
}
