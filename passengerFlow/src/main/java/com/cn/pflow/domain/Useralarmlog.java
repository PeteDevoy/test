package com.cn.pflow.domain;

import java.util.Date;

public class Useralarmlog extends UseralarmlogKey {
    private Long equipmentid;

    private String msgtype;

    private String readstatus;

    private String readtime;

    private Date createtime;

    private String note;

    public Long getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(Long equipmentid) {
        this.equipmentid = equipmentid;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype == null ? null : msgtype.trim();
    }

    public String getReadstatus() {
        return readstatus;
    }

    public void setReadstatus(String readstatus) {
        this.readstatus = readstatus == null ? null : readstatus.trim();
    }

    public String getReadtime() {
        return readtime;
    }

    public void setReadtime(String readtime) {
        this.readtime = readtime == null ? null : readtime.trim();
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
}