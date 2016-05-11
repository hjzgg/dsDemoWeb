package com.ds.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ds.entity.User;

@Repository
public interface UserDao extends JpaSpecificationExecutor<User>,
		PagingAndSortingRepository<User, Serializable> {
	
	@Query(value = "select * from user where username = ?1", nativeQuery=true)
	User findByName(String username);
}
