package dst.courseproject.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class AddCategoryBindingModel {

//    @NotEmpty(message = "Field cannot be empty.")
//    @Pattern(regexp = "^[a-zA-Z]+$", message = "Incorrect name format. ")
    private String name;

    public AddCategoryBindingModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
