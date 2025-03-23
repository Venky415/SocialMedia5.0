package com.project.socialMedia.service;

import com.project.socialMedia.entity.Feedback;
import com.project.socialMedia.entity.Message;
import com.project.socialMedia.repository.MessageRepository;
import com.project.socialMedia.response.FeedbackCreatorResponse;
import com.project.socialMedia.response.FeedbackResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository socialMessageRepository; // Repository for handling messages

    // Method to retrieve member feed
    public List<Message> getMemberFeed() {
        return socialMessageRepository.findAllByOrderByDateDesc();
    }

    // Method to create a new message
    public void createMessage(Message message) {
        socialMessageRepository.save(message);
    }

    // Method to retrieve a message by its ID
    public Optional<Message> getMessageById(Long messageId) {
        return socialMessageRepository.findById(messageId);
    }

    // Method to save (update) a message
    public void saveMessage(Message message) {
        socialMessageRepository.save(message);
    }

    // Method to delete a message by its ID
    public void deleteMessage(Long messageId) {
        socialMessageRepository.deleteById(messageId);
    }

    // Method to add a feedback to a message
    public void addFeedbackToMessage(Feedback feedback, Message message) {
        // Create response DTO for the feedback
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setFeedbackID(feedback.getFeedbackID());
        feedbackResponse.setFeedbackBody(feedback.getFeedbackBody());
        feedbackResponse.setFeedbackCreator(new FeedbackCreatorResponse(feedback.getFeedbackCreator().getUserID(), feedback.getFeedbackCreator().getName()));

        // Add feedback to the message and save
        List<Feedback> messageFeedbacks = message.getFeedbacks();
        messageFeedbacks.add(feedback);
        message.setFeedbacks(messageFeedbacks);
        socialMessageRepository.save(message);
    }
}
