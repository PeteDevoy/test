package com.cn.pflow.domain;

public class Pflowarea {
	private Long id;
	private String name;
	private String comments;
	private Long parentid;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Long getParentid() {
		return parentid;
	}
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	@Override
	public String toString() {
		return "Pflowarea [id=" + id + ", name=" + name + ", comments=" + comments + ", parentid=" + parentid + "]";
	}
	
}
