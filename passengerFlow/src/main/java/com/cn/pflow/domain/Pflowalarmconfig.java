package com.cn.pflow.domain;

public class Pflowalarmconfig {
    private Integer id;

    private Integer basicpeak;

    private Integer timeinterval;

    private Integer peak;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBasicpeak() {
        return basicpeak;
    }

    public void setBasicpeak(Integer basicpeak) {
        this.basicpeak = basicpeak;
    }

    public Integer getTimeinterval() {
        return timeinterval;
    }

    public void setTimeinterval(Integer timeinterval) {
        this.timeinterval = timeinterval;
    }

    public Integer getPeak() {
        return peak;
    }

    public void setPeak(Integer peak) {
        this.peak = peak;
    }
}