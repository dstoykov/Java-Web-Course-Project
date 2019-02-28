package dst.courseproject.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {

    @NotEmpty(message = "Field must not be empty. ")
    @Pattern(regexp = "^[a-z0-9._-]+@[a-z0-9-]+(\\.[a-z]{2,4}){1,3}$", message = "Invalid email format. ")
    private String email;

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
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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
