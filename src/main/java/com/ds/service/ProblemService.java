package com.ds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ds.entity.Problem;
import com.ds.repository.ProblemDao;

@Service
public class ProblemService {
	@Autowired
	private ProblemDao problemDao;
	
	@Transactional
	public Problem getProblemByName(String problemName){
		Problem problem = null;
		problem = problemDao.getOneByName(problemName);
		return problem;
	}
	
	@Transactional
	public Problem addProblem(String problemName){
		Problem problem = new Problem();
		problem.setProblemname(problemName);
		problemDao.save(problem);
		return problem;
	}
}
