package dst.courseproject.models.binding;

import javax.validation.constraints.NotEmpty;

public class CommentAddBindingModel {
    @NotEmpty(message = "You have to write comment!")
    private String content;

    public CommentAddBindingModel() {
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}