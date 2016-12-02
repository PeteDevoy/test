package com.cn.pflow.domain;



public class Pflowcount {
	
    private Long id;
	
    private Long equipmentid;
    
    private String mac;

    private String startdate;

    private String starttime;

    private String enddate;

    private String endtime;

    private Integer zoneid;

    private Long totalenters;

    private Long totalexits;

    private Integer enters;

    private Integer exits;

    private Integer status;
    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public Long getEquipmentid() {
		return equipmentid;
	}

	public void setEquipmentid(Long equipmentid) {
		this.equipmentid = equipmentid;
	}

	public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String string) {
        this.startdate = string;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String string) {
        this.starttime = string;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String string) {
        this.enddate = string;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String string) {
        this.endtime = string;
    }

    public Integer getZoneid() {
        return zoneid;
    }

    public void setZoneid(Integer zoneid) {
        this.zoneid = zoneid;
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

    public Integer getEnters() {
        return enters;
    }

    public void setEnters(Integer enters) {
        this.enters = enters;
    }

    public Integer getExits() {
        return exits;
    }

    public void setExits(Integer exits) {
        this.exits = exits;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}