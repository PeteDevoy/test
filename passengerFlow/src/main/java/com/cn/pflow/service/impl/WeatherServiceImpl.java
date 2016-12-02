package com.cn.pflow.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.pflow.IDao.WeatherMapper;
import com.cn.pflow.domain.WeatherInf;
import com.cn.pflow.service.WeatherService;

@Service("weatherService")
public class WeatherServiceImpl implements  WeatherService{
	@Resource
	private WeatherMapper weatherDao;

	@Override
	public void inweather(WeatherInf weatherInf) {
		 weatherDao.inweather(weatherInf);
		
	}

	@Override
	public int countweather(String date) {
		// TODO Auto-generated method stub
		return weatherDao.countweather(date);
	}
	
}
