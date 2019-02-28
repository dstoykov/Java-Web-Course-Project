package dst.courseproject.models.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class VideoAddBindingModel {

    @NotEmpty(message = "Title must not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9.,!? ]+$", message = "Title can only have capital and small letters, number and punctuation['!', '?', '.', ','].")
    @Size(max = 50, message = "Title must not be longer than 50 symbols.")
    private String title;

    @NotEmpty(message = "Description must not be empty.")
    private String description;

    @NotEmpty(message = "Category must not be empty.")
    private String category;

//    @NotEmpty(message = "You must select video file for uploading.")
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
