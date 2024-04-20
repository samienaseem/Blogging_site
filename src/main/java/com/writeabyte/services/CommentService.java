package com.writeabyte.services;

import com.writeabyte.entities.BlogPost;
import com.writeabyte.entities.Comment;
import com.writeabyte.entities.Like;
import com.writeabyte.entities.User;
import com.writeabyte.exceptions.BlogPostNotFoundException;
import com.writeabyte.exceptions.UserNotFoundException;
import com.writeabyte.models.requests.CommentRequest;
import com.writeabyte.models.response.CommentResponse;
import com.writeabyte.models.response.LikeResponse;
import com.writeabyte.repository.BlogPostRepository;
import com.writeabyte.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

import com.writeabyte.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserRepository userRepository;
    
    
    public List<CommentResponse> getCommentsByBlogPostId(Long blogPostId) {
        List<Comment> commentList =  commentRepository.findByBlogPostId(blogPostId);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (Comment comment: commentList) {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setCommentId(comment.getId());
            commentResponse.setComment(comment.getContent());
            commentResponse.setUserId(comment.getUser().getId());
            commentResponse.setBlogPostId(comment.getBlogPost().getId());
            commentResponseList.add(commentResponse);
        }
        return commentResponseList;
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }
    
    public CommentResponse addComment(CommentRequest commentRequest)  {
        CommentResponse commentResponse = new CommentResponse();
        BlogPost blogPost = blogPostRepository.findById(commentRequest.getBlogPostId())
            .orElseThrow(() -> new BlogPostNotFoundException("Blog post not found"));

        User user = userRepository.findById(commentRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Comment comment = new Comment();
        comment.setContent(commentRequest.getComment());
        comment.setBlogPost(blogPost);
        comment.setUser(user);

        Comment commentSaved = commentRepository.save(comment);
        if (commentSaved != null) {
            commentResponse.setStatus(true);
            commentResponse.setComment(commentSaved.getContent());
            commentResponse.setCommentId(commentSaved.getId());
            commentResponse.setUserId(commentSaved.getUser().getId());
        } else {
            commentResponse.setStatus(false);
        }

        return commentResponse;
    }

}