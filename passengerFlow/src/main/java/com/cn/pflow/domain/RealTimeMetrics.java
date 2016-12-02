package com.cn.pflow.domain;

public class RealTimeMetrics {

	private String macAddress;
	private String date;
	private String totalEnters;
	private String totalExits;
	
	
	public RealTimeMetrics(String macAddress, String date, String totalEnters, String totalExits) {
		super();
		this.macAddress = macAddress;
		this.date = date;
		this.totalEnters = totalEnters;
		this.totalExits = totalExits;
	}


	public String getMacAddress() {
		return macAddress;
	}


	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getTotalEnters() {
		return totalEnters;
	}


	public void setTotalEnters(String totalEnters) {
		this.totalEnters = totalEnters;
	}


	public String getTotalExits() {
		return totalExits;
	}


	public void setTotalExits(String totalExits) {
		this.totalExits = totalExits;
	}
	
	
	
}
