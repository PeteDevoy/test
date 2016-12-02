package com.cn.pflow.domain;

public class PflowActivity {
	private Long id;
	
	private String activitydate;
	
	private String name;
	
	private Long parentid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivitydate() {
		return activitydate;
	}

	public void setActivitydate(String activitydate) {
		this.activitydate = activitydate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	
	
}
