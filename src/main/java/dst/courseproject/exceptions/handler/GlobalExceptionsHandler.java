package dst.courseproject.exceptions.handler;

import com.dropbox.core.DbxException;
import dst.courseproject.exceptions.VideoAlreadyLiked;
import dst.courseproject.exceptions.VideoNotLiked;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler({DbxException.class, IOException.class, VideoAlreadyLiked.class, VideoNotLiked.class})
    public ModelAndView handleUploadingVideoExceptions(Exception e) {
        e.printStackTrace();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:../errors/error");

        return modelAndView;
    }
}
