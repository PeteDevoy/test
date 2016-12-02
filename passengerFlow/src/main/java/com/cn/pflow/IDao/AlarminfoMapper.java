package com.cn.pflow.IDao;

import com.cn.pflow.domain.Alarminfo;

public interface AlarminfoMapper {
    int deleteByPrimaryKey(String alarmid);

    int insert(Alarminfo record);

    int insertSelective(Alarminfo record);

    Alarminfo selectByPrimaryKey(String alarmid);

    int updateByPrimaryKeySelective(Alarminfo record);

    int updateByPrimaryKey(Alarminfo record);
}