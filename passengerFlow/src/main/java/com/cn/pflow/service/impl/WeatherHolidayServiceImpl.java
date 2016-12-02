package com.cn.pflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.pflow.IDao.WeatherHolidayMapper;
import com.cn.pflow.domain.HolidayResults;
import com.cn.pflow.domain.PflowActivity;
import com.cn.pflow.domain.Pflowarea;
import com.cn.pflow.domain.Pflowcount;
import com.cn.pflow.domain.WeatherInf;
import com.cn.pflow.service.WeatherHolidayService;
import com.cn.pflow.util.PageList;

@Service("weatherHolidayService")
public class WeatherHolidayServiceImpl implements  WeatherHolidayService{
	@Resource
	private WeatherHolidayMapper WeatherHolidayDao;
	
	@Override
	public List<Pflowarea> getPflowArea() {
		
		return WeatherHolidayDao.getPflowArea();
	}
	@Override
	public List<HolidayResults> getHolidayResults() {
		
		return WeatherHolidayDao.getHolidayResults();
	}
	@Override
	public List<PflowActivity> getPflowActivity() {
		
		return WeatherHolidayDao.getPflowActivity();
	}
	@Override
	public List<WeatherInf> getWeatherInfWeather() {
		
		return WeatherHolidayDao.getWeatherInfWeather();
	}
	@Override
	public List<WeatherInf> getWeatherInfTempadvise() {
		
		return WeatherHolidayDao.getWeatherInfTempadvise();
	}
	@Override
	public List<WeatherInf> getOneDayWeatherInfPmtwopointfive() {
		
		return WeatherHolidayDao.getOneDayWeatherInfPmtwopointfive();
	}
	
	
	

	@Override
	public Map<String, Object> getweatherholiday(int oneRecord, int pageNo,Map<String, Object> conditions) {
		List<Pflowcount> list = new ArrayList<Pflowcount>();
		Long count=WeatherHolidayDao.getGridCount(conditions);
		
		PageList pageList = new PageList();
		//pageNo 当前页
		pageList.setPageIndex(pageNo);
		//count 查询记录总数
		if (count != null)
			pageList.setRecordCount(count.intValue());
		//oneRecord 一页显示几条数据
		pageList.setPageSize(oneRecord);
		pageList.initialize();
		
		if(count != null && count > 0) {
			conditions.put("start", pageList.getStart());
			conditions.put("oneRecord", oneRecord);
			
			list = WeatherHolidayDao.selectGridCount(conditions);
		}
		
		Map<String, Object> charts=new HashMap<>();
		charts.put("rows", list);
		charts.put("total", pageList.getPageCount());		
		charts.put("records", count);
				
		return charts;
	}

	

	
	
}
