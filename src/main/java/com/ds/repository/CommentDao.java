package com.ds.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ds.entity.Comment;


public interface CommentDao extends JpaSpecificationExecutor<Comment>,
	PagingAndSortingRepository<Comment, String> {
	
	@Query(value = "select * from base_enum where pk_enum_id = ?1", nativeQuery=true)
	Comment getOneBaseEnum(String id);
}