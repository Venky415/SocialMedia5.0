package com.project.socialMedia.service;

import com.project.socialMedia.entity.Comment;
import com.project.socialMedia.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository socialCommentRepository; // Repository for handling comments

    // Method to create a new comment
    public void createComment(Comment comment) {
        socialCommentRepository.save(comment);
    }

    // Method to retrieve a comment by its ID
    public Optional<Comment> getCommentById(Long commentId) {
        return socialCommentRepository.findById(commentId);
    }

    // Method to save (update) a comment
    public void saveComment(Comment comment) {
        socialCommentRepository.save(comment);
    }

    // Method to delete a comment by its ID
    public void deleteComment(Long commentId) {
        socialCommentRepository.deleteById(commentId);
    }
}
