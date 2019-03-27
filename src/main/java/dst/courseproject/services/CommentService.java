package dst.courseproject.services;

import dst.courseproject.models.binding.CommentAddBindingModel;
import dst.courseproject.models.view.CommentViewModel;

import java.util.Set;

public interface CommentService {
    void save(CommentAddBindingModel bindingModel, String videoIdentifier, String principalEmail);

    Set<CommentViewModel> getCommentViewModelsByVideo(String videoIdentifier);
}
