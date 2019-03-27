package dst.courseproject.services;

import dst.courseproject.models.binding.CommentAddBindingModel;

public interface CommentService {
    void save(CommentAddBindingModel bindingModel, String videoIdentifier, String principalEmail);
}
