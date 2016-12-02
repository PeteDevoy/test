package com.cn.pflow.service;

import java.util.Map;

import com.cn.pflow.domain.WeatherInf;

public interface WeatherService {
	public int countweather(String  date);
	
	public void inweather(WeatherInf weatherInf);
}
