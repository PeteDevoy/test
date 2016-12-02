package com.cn.pflow.domain;



public class Charts {
	
	private String hour;
	
	private String day;
	
    private String startdate;

    private Integer sumenters;

    private Integer sumexits;
    
    private Integer avgenters;

    private Integer avgexits;
    
    private Long totalenters;

    private Long totalexits;

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
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

	public Long getTotalenters() {
		return totalenters;
	}

	public void setTotalenters(Long totalenters) {
		this.totalenters = totalenters;
	}

	public Long getTotalexits() {
		return totalexits;
	}

	public void setTotalexits(Long totalexits) {
		this.totalexits = totalexits;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}


	public Integer getAvgenters() {
		return avgenters;
	}

	public void setAvgenters(Integer avgenters) {
		this.avgenters = avgenters;
	}

	public Integer getAvgexits() {
		return avgexits;
	}

	public void setAvgexits(Integer avgexits) {
		this.avgexits = avgexits;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

    

}