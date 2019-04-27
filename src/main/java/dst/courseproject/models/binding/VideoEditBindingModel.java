package dst.courseproject.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class VideoEditBindingModel {
    private static final String TITLE_NOT_EMPTY_MSG = "Title must not be empty. ";
    private static final String TITLE_PATTERN_REGEXP = "^[a-zA-Z0-9.,!? '-]+$";
    private static final String TITLE_PATTERN_MSG = "Title can only have capital and small letters, number and punctuation[ '!', '?', ''', '-', '.', ','].";
    private static final int TITLE_SIZE_MAX = 50;
    private static final String TITLE_SIZE_MSG = "Title must not be longer than 50 symbols.";
    private static final String DESCRIPTION_NOT_EMPTY_MSG = "Description must not be empty.";
    private static final String CATEGORY_NOT_EMPTY_MSG = "You must choose a category.";

    private String identifier;

    @NotEmpty(message = TITLE_NOT_EMPTY_MSG)
    @Pattern(regexp = TITLE_PATTERN_REGEXP, message = TITLE_PATTERN_MSG)
    @Size(max = TITLE_SIZE_MAX, message = TITLE_SIZE_MSG)
    private String title;

    @NotEmpty(message = DESCRIPTION_NOT_EMPTY_MSG)
    private String description;

    @NotEmpty(message = CATEGORY_NOT_EMPTY_MSG)
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
