package com.ds.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the problem database table.
 * 
 */
@Entity
@Table(name="problem")
@NamedQuery(name="Problem.findAll", query="SELECT p FROM Problem p")
public class Problem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="uuid", strategy="uuid")
	@GeneratedValue(generator="uuid")
	@Column(name="problemid", length=32)
	private String problemid;
	
	@Column(name="problemname", length=50)
	private String problemname;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="problem", cascade={CascadeType.ALL}, fetch=FetchType.EAGER)//根据题目直接计算出该题目评论的数目
	private Set<Comment> comments;

	public Problem() {
	}

	public String getProblemid() {
		return this.problemid;
	}

	public void setProblemid(String problemid) {
		this.problemid = problemid;
	}

	public String getProblemname() {
		return this.problemname;
	}

	public void setProblemname(String problemname) {
		this.problemname = problemname;
	}

	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setProblem(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setProblem(null);
		return comment;
	}

}