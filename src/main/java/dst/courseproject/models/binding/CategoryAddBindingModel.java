package dst.courseproject.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class CategoryAddBindingModel {
    private static final String NAME_NOT_EMPTY_MSG = "Field cannot be empty.";
    private static final String NAME_PATTERN_REGEXP = "^[A-Z][a-zA-Z ]+$";
    private static final String NAME_PATTERN_MGS = "Incorrect name format. ";

    @NotEmpty(message = NAME_NOT_EMPTY_MSG)
    @Pattern(regexp = NAME_PATTERN_REGEXP, message = NAME_PATTERN_MGS)
    private String name;

    public CategoryAddBindingModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
