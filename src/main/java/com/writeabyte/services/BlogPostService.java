package com.writeabyte.services;

import com.writeabyte.entities.BlogPost;
import com.writeabyte.entities.User;
import com.writeabyte.exceptions.BlogPostNotFoundException;
import com.writeabyte.models.response.BlogPostResponse;
import com.writeabyte.models.response.BlogPosts;
import com.writeabyte.models.response.CommentResponse;
import com.writeabyte.repository.BlogPostRepository;
import com.writeabyte.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogPostService {
	@Autowired
	private BlogPostRepository blogPostRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LikeService likeService;

	@Autowired
	private CommentService commentService;

	private static final Logger logger = LoggerFactory.getLogger(BlogPostService.class);


    public List<BlogPost> getBlogPostsByUserId(Long userId) {
		return blogPostRepository.findByUserId(userId);
	}

	public BlogPosts createBlogPost(BlogPost blogPost) throws Exception {
		BlogPosts blogPosts = new BlogPosts();
		try {
			User user = userRepository.findById(blogPost.getUser().getId()).orElse(null);
			if (user == null) {
				throw new Exception("User not found with ID: " + blogPost.getUser().getId());
			}
			BlogPost savedBlogPost = blogPostRepository.save(blogPost);
			if (savedBlogPost !=null) {
				blogPosts.setId(savedBlogPost.getId());
				blogPosts.setContent(savedBlogPost.getContent());
				blogPosts.setTitle(savedBlogPost.getTitle());
				blogPosts.setUserId(savedBlogPost.getUser().getId());
			}
		} catch (Exception e){
			logger.error("Error while creating blog post", e);
		}
		return blogPosts;
	}

	public void deleteBlogPost(Long blogPostId) {
		if (!blogPostRepository.existsById(blogPostId)) {
			throw new BlogPostNotFoundException("Blog post not found with ID: " + blogPostId);
		}
		blogPostRepository.deleteById(blogPostId);
	}

	public BlogPostResponse getAllPostsWithLikesAndComments() {
		BlogPostResponse blogPostResponse = new BlogPostResponse();
		try {
			List<BlogPost> posts = blogPostRepository.findAll();
			List<BlogPosts> blogPosts = new ArrayList<BlogPosts>();
			blogPostResponse.setStatus(true);
			for (BlogPost post : posts) {
				BlogPosts blogPost = new BlogPosts();
				blogPost.setLikes(likeService.getLikesByBlogPostId(post.getId()).size());
				blogPost.setId(post.getId());
				blogPost.setUserId(post.getUser().getId());
				blogPost.setContent(post.getContent());
				blogPost.setTitle(post.getTitle());
				List<CommentResponse> comments = commentService.getCommentsByBlogPostId(post.getId());
				List<CommentResponse> blogComments = new ArrayList<>();
				for (CommentResponse c : comments) {
					c.setStatus(true);
					blogComments.add(c);
				}
				blogPost.setComments(blogComments);
				blogPosts.add(blogPost);
			}
			blogPostResponse.setBlogPosts(blogPosts);
		} catch (Exception e) {
			logger.error("Error in User Service", e);
		}

		return blogPostResponse;
	}

	public BlogPostResponse getCurrentUserAllPostsWithLikesAndComments(Long userId) {
		BlogPostResponse blogPostResponse = new BlogPostResponse();
		try {
			List<BlogPost> posts = blogPostRepository.findByUserId(userId);
			List<BlogPosts> blogPosts = new ArrayList<BlogPosts>();
			blogPostResponse.setStatus(true);
			for (BlogPost post : posts) {
				BlogPosts blogPost = new BlogPosts();
				blogPost.setLikes(likeService.getLikesByBlogPostId(post.getId()).size());
				blogPost.setId(post.getId());
				blogPost.setUserId(post.getUser().getId());
				blogPost.setContent(post.getContent());
				blogPost.setTitle(post.getTitle());
				List<CommentResponse> comments = commentService.getCommentsByBlogPostId(post.getId());
				List<CommentResponse> blogComments = new ArrayList<>();
				for (CommentResponse c : comments) {
					c.setStatus(true);
					blogComments.add(c);
				}
				blogPost.setComments(blogComments);
				blogPosts.add(blogPost);
			}
			blogPostResponse.setBlogPosts(blogPosts);
		} catch (Exception e) {
			logger.error("Error in User Service", e);
		}

		return blogPostResponse;
	}
}