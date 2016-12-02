package com.cn.pflow.IDao;

import org.apache.ibatis.annotations.Param;

import com.cn.pflow.domain.WeatherInf;

public interface WeatherMapper {
	public int countweather(@Param("date")String date);
	public void inweather(WeatherInf weatherInf);
}
