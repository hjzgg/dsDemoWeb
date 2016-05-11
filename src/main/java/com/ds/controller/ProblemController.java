package com.ds.controller;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.ds.entity.Comment;
import com.ds.entity.Problem;
import com.ds.service.CommentService;
import com.ds.service.ProblemService;
import com.ds.utils.QueryTool;

@Controller
public class ProblemController {
	
	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value="getCurProblemComments")
	@ResponseBody
	public String getCurProblemComments(String problemName, @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "4") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType){
		JSONObject jsono = new JSONObject();
		try {
			Problem problem = problemService.getProblemByName(problemName);
			if(problem == null){
				problem = problemService.addProblem(problemName);
			}
			PageRequest pageRequest = QueryTool.buildPageRequest(pageNumber, pageSize, sortType);
			//根据题目的id获取所有的评论
			Page<Comment> page = commentService.findAllComments(problem.getProblemid(), pageRequest);
			//对 Comment 这个类中的一些字段进行过滤
			SimplePropertyPreFilter spp = new SimplePropertyPreFilter();
			spp.getExcludes().addAll(Comment.getExcludeString());
			jsono.put("success", true);
			jsono.put("message", JSON.toJSONString(page, spp));
		} catch(Exception e){
			e.printStackTrace();
			jsono.put("success", false);
			jsono.put("message", "inner error.");
		}
		return jsono.toString();
	}
}
