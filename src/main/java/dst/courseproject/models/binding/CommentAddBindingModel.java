package dst.courseproject.models.binding;

import javax.validation.constraints.NotEmpty;

public class CommentAddBindingModel {
    private static final String CONTENT_NOT_EMPTY_MSG = "You have to write comment!";

    @NotEmpty(message = CONTENT_NOT_EMPTY_MSG)
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