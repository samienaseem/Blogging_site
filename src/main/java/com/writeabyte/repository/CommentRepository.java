package com.writeabyte.repository;

import com.writeabyte.entities.Comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByBlogPostId(Long blogPostId);
}