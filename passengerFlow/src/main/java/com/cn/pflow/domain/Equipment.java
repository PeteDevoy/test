package com.cn.pflow.domain;

public class Equipment {
    private Long equipmentid;

    private String name;

    private String type;

    private Long parentid;

    private String ip;

    private Integer port;

    private String macaddress;

    private String usetype;

    private Long area;

    private String lng;

    private String lat;

    private String comments;

    private String frsdeviceid;

    private String eqaccount;

    private String eqpwd;

    public Long getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(Long equipmentid) {
        this.equipmentid = equipmentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress == null ? null : macaddress.trim();
    }

    public String getUsetype() {
        return usetype;
    }

    public void setUsetype(String usetype) {
        this.usetype = usetype == null ? null : usetype.trim();
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng == null ? null : lng.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public String getFrsdeviceid() {
        return frsdeviceid;
    }

    public void setFrsdeviceid(String frsdeviceid) {
        this.frsdeviceid = frsdeviceid == null ? null : frsdeviceid.trim();
    }

    public String getEqaccount() {
        return eqaccount;
    }

    public void setEqaccount(String eqaccount) {
        this.eqaccount = eqaccount == null ? null : eqaccount.trim();
    }

    public String getEqpwd() {
        return eqpwd;
    }

    public void setEqpwd(String eqpwd) {
        this.eqpwd = eqpwd == null ? null : eqpwd.trim();
    }
}