package dst.courseproject.controllers;

import dst.courseproject.models.binding.CommentAddBindingModel;
import dst.courseproject.models.view.CommentViewModel;
import dst.courseproject.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/get")
    @ResponseBody
    public Set<CommentViewModel> getAllComments(@RequestParam("video") String videoIdentifier) {
        return this.commentService.getCommentViewModelsByVideo(videoIdentifier);
    }

    @PostMapping("/add")
    public void addComment(@RequestBody CommentAddBindingModel bindingModel, @RequestParam("video") String videoIdentifier, Principal principal) {
        this.commentService.save(bindingModel, videoIdentifier, principal.getName());
    }

}