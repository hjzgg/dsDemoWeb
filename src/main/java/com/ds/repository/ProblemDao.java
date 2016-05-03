package com.ds.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ds.entity.Problem;

public interface ProblemDao extends JpaSpecificationExecutor<Problem>,
	PagingAndSortingRepository<Problem, String> {
	
	@Query(value = "select * from base_enum where pk_enum_id = ?1", nativeQuery=true)
	Problem getOneBaseEnum(String id);
}
