package com.ds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.entity.Comment;
import com.ds.repository.CommentDao;

@Service
public class CommentService {
	@Autowired
	private CommentDao commentDao;
	
	@Transactional
	public void addComment(Comment comment) {
		if(comment == null) return;
		commentDao.save(comment);
	}
	
	@Transactional
	public Page<Comment> findAllComments(String problemId, PageRequest pageable){
		Page<Comment> page = commentDao.findAllCommentsByProblemId(problemId, pageable);
		return page;
	}

	public Page<Comment> findAllComments2(Specification<Comment> spec, PageRequest pageRequest) {
		return commentDao.findAll(spec, pageRequest);
	}
}
