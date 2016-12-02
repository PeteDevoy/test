package com.cn.pflow.domain;

public class ChartsCount {
	
	private Long id;
	
    private String mac;
    
    private String startdatetime;
    
    private String enddatetime;
    
    private Integer timehour;
    
    private Integer timeminute;
    
    private Integer sumenters;
    
    private Integer sumexits;
    
    private Integer zoneid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getStartdatetime() {
		return startdatetime;
	}

	public void setStartdatetime(String startdatetime) {
		this.startdatetime = startdatetime;
	}

	public String getEnddatetime() {
		return enddatetime;
	}

	public void setEnddatetime(String enddatetime) {
		this.enddatetime = enddatetime;
	}

	public Integer getTimehour() {
		return timehour;
	}

	public void setTimehour(Integer timehour) {
		this.timehour = timehour;
	}

	public Integer getTimeminute() {
		return timeminute;
	}

	public void setTimeminute(Integer timeminute) {
		this.timeminute = timeminute;
	}

	public Integer getSumenters() {
		return sumenters;
	}

	public void setSumenters(Integer sumenters) {
		this.sumenters = sumenters;
	}

	public Integer getSumexits() {
		return sumexits;
	}

	public void setSumexits(Integer sumexits) {
		this.sumexits = sumexits;
	}

	public Integer getZoneid() {
		return zoneid;
	}

	public void setZoneid(Integer zoneid) {
		this.zoneid = zoneid;
	}
}
