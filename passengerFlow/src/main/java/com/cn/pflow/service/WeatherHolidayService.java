package com.cn.pflow.service;

import java.util.List;
import java.util.Map;

import com.cn.pflow.domain.HolidayResults;
import com.cn.pflow.domain.PflowActivity;
import com.cn.pflow.domain.Pflowarea;
import com.cn.pflow.domain.WeatherInf;

public interface WeatherHolidayService {
	
	public List<Pflowarea> getPflowArea();//查询案场区域
	
	public List<HolidayResults> getHolidayResults();//查询节假日
	
	public List<PflowActivity> getPflowActivity();//查询活动
	
	public List<WeatherInf> getWeatherInfWeather();//查询天气
	
	public List<WeatherInf> getWeatherInfTempadvise();//查询温度
	
	public List<WeatherInf> getOneDayWeatherInfPmtwopointfive();//查询温度
	
	public Map<String, Object> getweatherholiday(int oneRecord,  int pageNo,Map<String, Object> conditions);
}
