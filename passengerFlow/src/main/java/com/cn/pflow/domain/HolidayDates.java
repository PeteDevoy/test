package com.cn.pflow.domain;

public class HolidayDates {
	private String date;
	private Integer status;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "HolidayDates [date=" + date + ", status=" + status + "]";
	}
	
}
