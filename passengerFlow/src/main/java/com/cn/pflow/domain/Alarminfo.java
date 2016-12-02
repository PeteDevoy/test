package com.cn.pflow.domain;

import java.util.Date;

public class Alarminfo {
    private String alarmid;

    private Date createtime;

    private String note;

    private Long equipmentid;

    private String alarmtype;

    private Integer frsalarmid;

    public String getAlarmid() {
        return alarmid;
    }

    public void setAlarmid(String alarmid) {
        this.alarmid = alarmid == null ? null : alarmid.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Long getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(Long equipmentid) {
        this.equipmentid = equipmentid;
    }

    public String getAlarmtype() {
        return alarmtype;
    }

    public void setAlarmtype(String alarmtype) {
        this.alarmtype = alarmtype == null ? null : alarmtype.trim();
    }

    public Integer getFrsalarmid() {
        return frsalarmid;
    }

    public void setFrsalarmid(Integer frsalarmid) {
        this.frsalarmid = frsalarmid;
    }
}