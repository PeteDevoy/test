package com.cn.pflow.IDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.pflow.domain.Charts;
import com.cn.pflow.domain.Pflowcount;
import com.cn.pflow.domain.PflowcountSelect;

public interface ChartsServiceMapper {

	List<Object> getSeventyChartsInfoByTime(String startDate, String endDate,String startTime, String endTime,@Param("equipmentids")List<Long> equipmentids);
	
	
	List<Object> getEightyChartsInfoByTime(String startDate, String endDate,String startTime, String endTime,@Param("equipmentids")List<Long> equipmentids);
	
	
	List<Object> getNinetyChartsInfoByTime(String startDate, String endDate,String startTime, String endTime,@Param("equipmentids")List<Long> equipmentids);
    
}