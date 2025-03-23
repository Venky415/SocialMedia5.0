package com.project.socialMedia.controller;

import com.project.socialMedia.entity.Post;
import com.project.socialMedia.entity.User;
import com.project.socialMedia.request.EditPostRequest;
import com.project.socialMedia.request.PostRequest;
import com.project.socialMedia.service.PostService;
import com.project.socialMedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/post") // Prefix for post-related endpoints
public class PostController {

    @Autowired
    private PostService socialPostService; // Service for handling posts

    @Autowired
    private UserService socialUserService; // Service for handling users

    // Endpoint to create a new post
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequest request) {
        // Check if the user exists
        Optional<User> userOptional = Optional.ofNullable(socialUserService.getUserById(request.getUserID()));
        if (userOptional.isPresent()) {
            // Create and save the post
            Post post = new Post();
            post.setPostBody(request.getPostBody());
            post.setUserId(request.getUserID());
            post.setDate(new Date());
            socialPostService.createPost(post);
            return ResponseEntity.ok("Post created successfully");
        } else {
            // User not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }
    }

    // Endpoint to retrieve a post by ID
    @GetMapping
    public ResponseEntity<?> getPost(@RequestParam Long postID) {
        // Retrieve the post by ID
        Optional<Post> postOptional = socialPostService.getPostById(postID);
        if (postOptional.isPresent()) {
            // Return the post if found
            return ResponseEntity.ok(postOptional.get());
        } else {
            // Post not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }
    }

    // Endpoint to edit an existing post
    @PatchMapping
    public ResponseEntity<String> editPost(@RequestBody EditPostRequest request) {
        // Retrieve the post by ID
        Optional<Post> postOptional = socialPostService.getPostById(request.getPostID());
        if (postOptional.isPresent()) {
            // Update the post body and save
            Post post = postOptional.get();
            post.setPostBody(request.getPostBody());
            socialPostService.savePost(post);
            return ResponseEntity.ok("Post edited successfully");
        } else {
            // Post not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }
    }

    // Endpoint to delete a post by ID
    @DeleteMapping
    public ResponseEntity<String> deletePost(@RequestParam Long postID) {
        // Retrieve the post by ID
        Optional<Post> postOptional = socialPostService.getPostById(postID);
        if (postOptional.isPresent()) {
            // Delete the post if found
            socialPostService.deletePost(postID);
            return ResponseEntity.ok("Post deleted");
        } else {
            // Post not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post does not exist");
        }
    }
}
