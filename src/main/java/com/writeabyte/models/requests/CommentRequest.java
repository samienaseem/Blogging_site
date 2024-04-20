package com.writeabyte.models.requests;

public class CommentRequest {

	private Long userId;
	private Long blogPostId;
	private String comment;

	public Long getBlogPostId() {
		return blogPostId;
	}

	public void setBlogPostId(Long blogPostId) {
		this.blogPostId = blogPostId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
