package com.cn.pflow.domain;

import java.util.List;

public class Holiday {
	private String name;
	private List<HolidayDates> list;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<HolidayDates> getList() {
		return list;
	}
	public void setList(List<HolidayDates> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "Holiday [name=" + name + ", list=" + list + "]";
	}
	
}
