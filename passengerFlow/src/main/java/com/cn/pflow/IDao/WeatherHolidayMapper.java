package com.cn.pflow.IDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cn.pflow.domain.HolidayResults;
import com.cn.pflow.domain.PflowActivity;
import com.cn.pflow.domain.Pflowarea;
import com.cn.pflow.domain.Pflowcount;
import com.cn.pflow.domain.WeatherInf;


public interface WeatherHolidayMapper {
	
	public List<Pflowarea> getPflowArea();//查询案场区域
	
	public List<HolidayResults> getHolidayResults();//查询案场区域
	
	public List<PflowActivity> getPflowActivity();//查询案场区域
	
	public List<WeatherInf> getWeatherInfWeather();//查询案场区域
	
	public List<WeatherInf> getWeatherInfTempadvise();//查询案场区域
	
	public List<WeatherInf> getOneDayWeatherInfPmtwopointfive();//查询案场区域

	public Long getGridCount(Map<String, Object> conditions);
	
	public List<Pflowcount> selectGridCount(Map<String, Object> conditions);



}
