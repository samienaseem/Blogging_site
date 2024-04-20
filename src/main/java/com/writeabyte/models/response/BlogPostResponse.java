package com.writeabyte.models.response;

import java.util.List;

public class BlogPostResponse {
	boolean status;
	List<BlogPosts> blogPosts;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<BlogPosts> getBlogPosts() {
		return blogPosts;
	}

	public void setBlogPosts(List<BlogPosts> blogPosts) {
		this.blogPosts = blogPosts;
	}

}
