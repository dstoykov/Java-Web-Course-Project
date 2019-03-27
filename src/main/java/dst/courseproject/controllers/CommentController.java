package dst.courseproject.controllers;

import dst.courseproject.models.binding.CommentAddBindingModel;
import dst.courseproject.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments-get")
    public ResponseEntity<Object> getAllComments() {
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<CommentAddBindingModel> addComment(@RequestBody CommentAddBindingModel bindingModel, @RequestParam("video") String videoIdentifier, Principal principal) {
        this.commentService.save(bindingModel, videoIdentifier, principal.getName());
        return new ResponseEntity<>(bindingModel, HttpStatus.OK);
    }

}
