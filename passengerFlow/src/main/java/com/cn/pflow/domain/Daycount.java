package com.cn.pflow.domain;

import java.util.Date;

public class Daycount {
    private Integer id;

    private Long equipmentid;

    private Date date;

    private Long totalenters;

    private Long totalexits;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(Long equipmentid) {
        this.equipmentid = equipmentid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}