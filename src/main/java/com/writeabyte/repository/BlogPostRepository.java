package com.writeabyte.repository;

import com.writeabyte.entities.BlogPost;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
	List<BlogPost> findAll();

	List<BlogPost> findByUserId(Long userId);
}
