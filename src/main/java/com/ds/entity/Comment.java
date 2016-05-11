package com.ds.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the comment database table.
 * 
 */
@Entity
@Table(name="comment")
@NamedQuery(name="Comment.findAll", query="SELECT c FROM Comment c")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="uuid", strategy="uuid")
	@GeneratedValue(generator="uuid")
	@Column(name="commentid")
	private String commentid;

	@Lob
	@Column(name="ccontent")
	private String ccontent;

	@Column(name="ctime")
	private Timestamp ctime = new Timestamp(System.currentTimeMillis());

	//bi-directional many-to-one association to Problem
	@ManyToOne(targetEntity=Problem.class, fetch=FetchType.LAZY)
	@JoinColumn(name="problemid")
	private Problem problem;

	//bi-directional many-to-one association to User
	@ManyToOne(targetEntity=User.class, fetch=FetchType.EAGER)//可以显示出评论的人数
	@JoinColumn(name="userid")
	private User user;

	public Comment() {
	}

	public String getCommentid() {
		return this.commentid;
	}

	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}

	public String getCcontent() {
		return this.ccontent;
	}

	public void setCcontent(String ccontent) {
		this.ccontent = ccontent;
	}

	public Timestamp getCtime() {
		return this.ctime;
	}

	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}

	public Problem getProblem() {
		return this.problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public static List<String> getExcludeString(){
		List<String> exc = new ArrayList<String>();
		exc.add("user");
		exc.add("problem");
		exc.add("ctime");
		return exc;
	}
}