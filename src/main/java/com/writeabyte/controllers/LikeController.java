package com.writeabyte.controllers;

import java.util.List;

import com.writeabyte.models.response.LikeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.writeabyte.entities.Like;
import com.writeabyte.services.LikeService;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
	@Autowired
	private LikeService likeService;

	@GetMapping("/blog-post/{blogPostId}")
    public ResponseEntity<List<LikeResponse>> getLikesByBlogPostId(@PathVariable Long blogPostId) {
        List<LikeResponse> likes = likeService.getLikesByBlogPostId(blogPostId);
        return ResponseEntity.ok(likes);
    }
	
	@PostMapping("/like")
	public ResponseEntity<?> likeBlogPost(@RequestParam Long userId, @RequestParam Long blogPostId) {
		try {
			Like like = likeService.likeBlogPost(userId, blogPostId);
			if(like == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Post Not Found");
			}
			return ResponseEntity.ok("Blog post liked successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to like blog post: " + e.getMessage());
		}
	}

	@DeleteMapping("/unlike")
	public ResponseEntity<?> unlikeBlogPost(@RequestParam Long userId, @RequestParam Long blogPostId) {
		try {
			Like like = likeService.unlikeBlogPost(userId, blogPostId);
			if(like == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Post Not Found");
			}
			return ResponseEntity.ok("Blog post unliked successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to unlike blog post: " + e.getMessage());
		}
	}

}
