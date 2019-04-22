package dst.courseproject.controllers;

import dst.courseproject.models.binding.CommentAddBindingModel;
import dst.courseproject.models.service.CommentServiceModel;
import dst.courseproject.models.service.UserServiceModel;
import dst.courseproject.models.view.CommentViewModel;
import dst.courseproject.services.CommentService;
import dst.courseproject.services.UserService;
import dst.courseproject.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/get")
    @ResponseBody
    public Set<CommentViewModel> getAllComments(@RequestParam("video") String videoIdentifier) {
        return this.commentService.getCommentViewModelsByVideo(videoIdentifier);
    }

    @PostMapping("/add")
    public boolean addComment(@RequestBody CommentAddBindingModel bindingModel, @RequestParam("video") String videoIdentifier, Principal principal) {
        this.commentService.save(bindingModel, videoIdentifier, principal.getName());

        return true;
    }

    @PostMapping("/remove")
    public boolean removeComment(@RequestParam("comment") String commentId, Principal principal) {
        CommentServiceModel commentServiceModel = this.commentService.getCommentServiceModelById(commentId);
        UserServiceModel userServiceModel = this.userService.getUserServiceModelByEmail(principal.getName());
        if (UserUtils.hasRole("MODERATOR", userServiceModel.getAuthorities()) || userServiceModel.getEmail().equals(commentServiceModel.getAuthor().getEmail())) {
            this.commentService.remove(commentId);

            return true;
        }

        return false;
    }
}