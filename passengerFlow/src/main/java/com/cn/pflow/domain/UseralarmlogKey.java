package com.cn.pflow.domain;

public class UseralarmlogKey {
    private Long userid;

    private String alarmid;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getAlarmid() {
        return alarmid;
    }

    public void setAlarmid(String alarmid) {
        this.alarmid = alarmid == null ? null : alarmid.trim();
    }
}