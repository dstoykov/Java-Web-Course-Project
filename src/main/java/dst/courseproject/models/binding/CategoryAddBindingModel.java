package dst.courseproject.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class CategoryAddBindingModel {

    @NotEmpty(message = "Field cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Incorrect name format. ")
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
