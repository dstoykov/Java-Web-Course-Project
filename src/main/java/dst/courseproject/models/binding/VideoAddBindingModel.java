package dst.courseproject.models.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class VideoAddBindingModel {
    private static final String TITLE_NOT_EMPTY_MSG = "Title must not be empty. ";
    private static final String TITLE_PATTERN_REGEXP = "^[a-zA-Z0-9.,!? '-]+$";
    private static final String TITLE_PATTERN_MSG = "Title can only have capital and small letters, number and punctuation[ '!', '?', ''', '-', '.', ','].";
    private static final int TITLE_SIZE_MAX = 50;
    private static final String TITLE_SIZE_MSG = "Title must not be longer than 50 symbols.";
    private static final String DESCRIPTION_NOT_EMPTY_MSG = "Description must not be empty.";
    private static final String CATEGORY_NOT_EMPTY_MSG = "You must choose a category.";
    private final static String VIDEO_FILE_NOT_NULL_MSG = "You must select video file for uploading.";

    @NotEmpty(message = TITLE_NOT_EMPTY_MSG)
    @Pattern(regexp = TITLE_PATTERN_REGEXP, message = TITLE_PATTERN_MSG)
    @Size(max = TITLE_SIZE_MAX, message = TITLE_SIZE_MSG)
    private String title;

    @NotEmpty(message = DESCRIPTION_NOT_EMPTY_MSG)
    private String description;

    @NotEmpty(message = CATEGORY_NOT_EMPTY_MSG)
    private String category;

    @NotNull(message = VIDEO_FILE_NOT_NULL_MSG)
    private MultipartFile videoFile;

    public VideoAddBindingModel() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MultipartFile getVideoFile() {
        return this.videoFile;
    }

    public void setVideoFile(MultipartFile videoFile) {
        this.videoFile = videoFile;
    }
}
