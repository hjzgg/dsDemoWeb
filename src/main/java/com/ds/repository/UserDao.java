package com.ds.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ds.entity.User;

public interface UserDao extends JpaSpecificationExecutor<User>,
		PagingAndSortingRepository<User, String> {
	
	@Query(value = "select * from user where username = ?1", nativeQuery=true)
	User findByName(String username);
}
