package com.cn.pflow.service;

import java.util.List;

import com.cn.pflow.domain.Charts;
import com.cn.pflow.domain.ChartsCount;
import com.cn.pflow.domain.Pflowcount;


/**
 * 报表数据接口
 * @author james
 *
 */

public interface IChartsService {

	//获取当日进入总数
	public String getTotalEntersCount();
	
	//
	public List<Pflowcount> getPflowCountInfoByTime(String startDate, String endDate, String startTime, String endTime);
	
	public List<Object> getChartsCountInfoByTime(String startDates, String endDates,int tspan,List<Long> equipmentids);
	
	public List<Object> getSeventyChartsInfoByTime(String startDate, String endDate, String startTime, String endTime,List<Long> equipmentids);
	
	public List<Object> getEightyChartsInfoByTime(String startDate, String endDate, String startTime, String endTime,List<Long> equipmentids);
	
	public List<Object> getNinetyChartsInfoByTime(String startDate, String endDate, String startTime, String endTime,List<Long> equipmentids);

}
