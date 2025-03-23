package com.project.socialMedia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentID; // Unique identifier for the comment

    private String commentBody; // Text content of the comment

    // Creator of the comment (response DTO), removed @JsonIgnore to allow serialization
    private CommentCreatorResponse commentCreator;

    @JsonIgnore
    private Long postID; // ID of the associated post, hidden from serialization

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false) // Mapping to the associated post
    private Post post; // Associated post for the comment
}
