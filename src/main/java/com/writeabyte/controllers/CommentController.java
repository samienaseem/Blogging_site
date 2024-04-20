package com.writeabyte.controllers;

import java.util.List;

import com.writeabyte.models.response.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.writeabyte.entities.Comment;
import com.writeabyte.models.requests.CommentRequest;
import com.writeabyte.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping("/blog-post/{blogPostId}")
	public ResponseEntity<List<CommentResponse>> getCommentsByBlogPostId(@PathVariable Long blogPostId) {
		List<CommentResponse> comments = commentService.getCommentsByBlogPostId(blogPostId);
		return ResponseEntity.ok(comments);
	}

	@PostMapping("/add")
	public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest comment)  {
		CommentResponse createdComment = commentService.addComment(comment);
		return ResponseEntity.ok(createdComment);
	}

}
