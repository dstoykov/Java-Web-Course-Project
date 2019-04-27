package dst.courseproject.thumbnail;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class ThumbnailExtractor {
    private static final String VIDEO_PATH = "C:\\Users\\Димитър\\Dropbox\\";
    private static final String MP4 = ".mp4";
    private static final String JPG = ".jpg";
    private static final String FORMAT_NAME = "jpg";
    private static final Integer FRAME_NUMBER = 20;

    public File grab(String videoName) throws FrameGrabber.Exception, IOException {
        Java2DFrameConverter converter = new Java2DFrameConverter();
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(VIDEO_PATH + videoName);
        frameGrabber.start();
        frameGrabber.setFrameNumber(FRAME_NUMBER);
        Frame frame = frameGrabber.grab();
        BufferedImage bufferedImage = converter.convert(frame);

        File file = new File(videoName.replace(MP4, JPG));
        ImageIO.write(bufferedImage, FORMAT_NAME, file);

        frameGrabber.stop();

        return file;
    }
}