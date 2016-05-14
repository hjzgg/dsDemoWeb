package com.ds.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.ds.entity.Comment;
import com.ds.entity.Problem;
import com.ds.entity.User;
import com.ds.service.CommentService;
import com.ds.service.ProblemService;
import com.ds.service.UserService;
import com.ds.utils.QueryTool;

@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProblemService problemService;
	
	@RequestMapping(value="addComment")
	@ResponseBody
	public String addComment(String username, String problemName, String content, @RequestParam(value="pageSize", defaultValue="2")int pageSize){
		JSONObject jsono = new JSONObject();
		try{
			Problem problem = problemService.getProblemByName(problemName);
			User user = userService.findOneUser(username);
			Comment comment = new Comment();
			comment.setProblem(problem);
			comment.setUser(user);
			comment.setCcontent(content);
			commentService.addComment(comment);
			
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put(Operator.EQ + "_problem.problemid", problem.getProblemid());
		    PageRequest pageRequest = QueryTool.buildPageRequest(0, pageSize, "auto");
		    Specification<Comment> spec = QueryTool.buildSpecification(searchParams, Comment.class);
		    Page<Comment> page = commentService.findAllComments2(spec, pageRequest);
		    //对 Comment 这个类中的一些字段进行过滤
			SimplePropertyPreFilter spp = new SimplePropertyPreFilter();
			spp.getExcludes().addAll(Comment.getExcludeString());
			jsono.put("success", true);
			jsono.put("message", JSON.toJSONString(page, spp));
		} catch (Exception ex){
			ex.printStackTrace();
			jsono.put("success", false);
			jsono.put("message", "inner error.");
		}
		return jsono.toString();
	}
}
