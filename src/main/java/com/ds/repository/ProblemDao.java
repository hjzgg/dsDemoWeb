package com.ds.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ds.entity.Problem;

@Repository
public interface ProblemDao extends JpaSpecificationExecutor<Problem>,
	PagingAndSortingRepository<Problem, String> {
	
	@Query(value = "select * from problem where problemname = ?1", nativeQuery=true)
	Problem getOneByName(String problemName);
}
