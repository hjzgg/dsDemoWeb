package com.ds.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ds.entity.Comment;

@Repository
public interface CommentDao extends JpaSpecificationExecutor<Comment>,
	PagingAndSortingRepository<Comment, String> {
	
	@Query("select c from Comment c where c.problem.problemid = ?1")
	public Page<Comment> findAllCommentsByProblemId(String problemId, Pageable pageable);
}
