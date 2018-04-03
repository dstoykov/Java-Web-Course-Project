package dst.courseproject.models.binding;

import javax.validation.constraints.NotEmpty;

public class AddCategoryBindingModel {

    @NotEmpty
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
