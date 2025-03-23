package com.project.socialMedia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("postID") // Renamed the property for serialization
    private Long id; // Unique identifier for the post

    @Column(nullable = false)
    private String postBody; // Text content of the post

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date; // Date of the post creation

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL) // One-to-many relationship with comments
    private List<Comment> comments; // List of comments associated with the post

    @JsonIgnore
    @Column(name = "user_id", nullable = false)
    private Long userId; // ID of the user who created the post

    // Getter and setter methods for the user ID
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getter and setter methods for the post ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and setter methods for the post body
    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    // Getter and setter methods for the post creation date
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Getter and setter methods for the list of comments associated with the post
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    // Method to return the formatted date for serialization
    @JsonProperty("date")
    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(this.date);
    }
}
