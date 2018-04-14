package dst.courseproject.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserEditBindingModel {
    private String id;

    @NotEmpty(message = "Field name must not be empty. ")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Incorrect name format. ")
    private String firstName;

    @NotEmpty(message = "Field name must not be empty. ")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Incorrect name format. ")
    private String lastName;

    @NotEmpty(message = "Field name must not be empty. ")
    @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 symbols. ")
    private String password;

    @NotEmpty(message = "Field name must not be empty. ")
    @Size(min = 8, max = 20, message = "Password confirmation length must be between 8 and 20 symbols. ")
    private String confirmPassword;

    public UserEditBindingModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
