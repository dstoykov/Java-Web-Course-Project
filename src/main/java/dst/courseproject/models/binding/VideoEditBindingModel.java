package dst.courseproject.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class VideoEditBindingModel {
    private String identifier;

    @NotEmpty(message = "Title must not be empty. ")
    @Pattern(regexp = "^[a-zA-Z0-9.,!? '-]+$", message = "Title can only have capital and small letters, number and punctuation[ '!', '?', ''', '-', '.', ','].")
    @Size(max = 50, message = "Title must not be longer than 50 symbols.")
    private String title;

    @NotEmpty(message = "Description must not be empty.")
    private String description;

    @NotEmpty(message = "You must choose a category.")
    private String category;

    public VideoEditBindingModel() {
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
}
