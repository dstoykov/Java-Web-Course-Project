package dst.courseproject.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class CategoryEditBindingModel {

    private String id;

    @NotEmpty(message = "Field cannot be empty.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Incorrect name format. ")
    private String name;

    public CategoryEditBindingModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
