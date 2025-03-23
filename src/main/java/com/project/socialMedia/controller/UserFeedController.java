package com.project.socialMedia.controller;

import com.project.socialMedia.entity.Post;
import com.project.socialMedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-feed") // Endpoint prefix for user feed related operations
public class UserFeedController {

    @Autowired
    private PostService socialPostService; // Service for handling posts

    // Endpoint to retrieve user feed
    @GetMapping
    public List<Post> getUserFeed() {
        // Retrieve and return user feed
        return socialPostService.getUserFeed();
    }
}
