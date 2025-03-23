package com.project.socialMedia.controller;

import com.project.socialMedia.entity.Comment;
import com.project.socialMedia.entity.Post;
import com.project.socialMedia.entity.User;
import com.project.socialMedia.request.CreateCommentRequest;
import com.project.socialMedia.request.EditCommentRequest;
import com.project.socialMedia.response.CommentResponse;
import com.project.socialMedia.service.CommentService;
import com.project.socialMedia.service.PostService;
import com.project.socialMedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/comment") // Prefix for comment-related endpoints
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService socialCommentService, UserService socialUserService, PostService socialPostService) {
        this.commentService = socialCommentService;
        this.userService = socialUserService;
        this.postService = socialPostService;
    }

    // Endpoint to create a new comment
    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CreateCommentRequest request) {
        // Check if the post exists
        Optional<Post> postOptional = postService.getPostById(request.getPostID());
        if (!postOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }

        // Check if the user exists
        Optional<User> userOptional = Optional.ofNullable(userService.getUserById(request.getUserID()));
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }

        // Create and save the comment
        Comment comment = new Comment();
        comment.setCommentBody(request.getCommentBody());
        comment.setCommentCreator(new CommentCreatorResponse(userOptional.get().getId(), userOptional.get().getName()));
        comment.setPostID(request.getPostID());
        comment.setPost(postOptional.get());
        commentService.createComment(comment);
        postService.addComment(comment, postOptional.get());

        return ResponseEntity.ok("Comment created successfully");
    }

    // Endpoint to retrieve a comment by ID
    @GetMapping
    public ResponseEntity<?> getComment(@RequestParam Long commentID) {
        // Retrieve the comment by ID
        Optional<Comment> commentOptional = commentService.getCommentById(commentID);
        if (!commentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
        // Map comment entity to response DTO
        Comment comment = commentOptional.get();
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentID(comment.getCommentID());
        commentResponse.setCommentBody(comment.getCommentBody());
        commentResponse.setCommentCreator(new CommentCreatorResponse(comment.getCommentCreator().getUserID(), comment.getCommentCreator().getName()));

        return ResponseEntity.ok(commentResponse);
    }

    // Endpoint to edit an existing comment
    @PatchMapping
    public ResponseEntity<String> editComment(@RequestBody EditCommentRequest request) {
        // Retrieve the comment by ID
        Optional<Comment> commentOptional = commentService.getCommentById(request.getCommentID());
        if (!commentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
        // Update the comment body and save
        Comment comment = commentOptional.get();
        comment.setCommentBody(request.getCommentBody());
        commentService.saveComment(comment);
        return ResponseEntity.ok("Comment edited successfully");
    }

    // Endpoint to delete a comment by ID
    @DeleteMapping
    public ResponseEntity<String> deleteComment(@RequestParam Long commentID) {
        // Retrieve the comment by ID
        Optional<Comment> commentOptional = commentService.getCommentById(commentID);
        if (!commentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment does not exist");
        }
        // Delete the comment
        commentService.deleteComment(commentID);
        return ResponseEntity.ok("Comment deleted");
    }
}
