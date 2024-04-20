package com.writeabyte.services;

import com.writeabyte.entities.BlogPost;
import com.writeabyte.entities.Like;
import com.writeabyte.entities.User;
import com.writeabyte.models.response.LikeResponse;
import com.writeabyte.repository.BlogPostRepository;
import com.writeabyte.repository.LikeRepository;
import com.writeabyte.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BlogPostRepository blogPostRepository;

	public List<LikeResponse> getLikesByBlogPostId(Long blogPostId) {
		List<Like> likesList =  likeRepository.findByBlogPostId(blogPostId);
		List<LikeResponse> likeResponses = new ArrayList<>();
		for (Like like: likesList) {
			LikeResponse likeResponse = new LikeResponse();
			likeResponse.setLikeId(like.getId());
			likeResponse.setUserId(like.getUser().getId());
			likeResponse.setBlogPostId(like.getBlogPost().getId());
			likeResponses.add(likeResponse);
		}
		return likeResponses;
	}

	public boolean hasUserLikedBlogPost(Long userId, Long blogPostId) {
		return likeRepository.existsByUserIdAndBlogPostId(userId, blogPostId);
	}

	public Like likeBlogPost(Long userId, Long blogPostId) {
		Like likedPost = null;
		if (!hasUserLikedBlogPost(userId, blogPostId)) {
			Like like = new Like();
			Optional<User> user = userRepository.findById(userId);
			Optional<BlogPost> blogPost = blogPostRepository.findById(blogPostId);
			if (user.isPresent() && blogPost.isPresent()) {
				like.setUser(user.get());
				like.setBlogPost(blogPost.get());
				likedPost = likeRepository.save(like);
			}
		}
		return likedPost;
	}

	public Like unlikeBlogPost(Long userId, Long blogPostId) {
		Like likedPost = null;
		if (hasUserLikedBlogPost(userId, blogPostId)) {
			likedPost = likeRepository.deleteByUserIdAndBlogPostId(userId, blogPostId);
			Like like = new Like();
			Optional<User> user = userRepository.findById(userId);
			Optional<BlogPost> blogPost = blogPostRepository.findById(blogPostId);
			if (user.isPresent() && blogPost.isPresent()) {
				like.setUser(user.get());
				like.setBlogPost(blogPost.get());
				likedPost = likeRepository.save(like);
			}
		}
		return likedPost;
	}
}