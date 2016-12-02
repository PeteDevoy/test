package com.cn.pflow.IDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.pflow.domain.ChartsCount;

public interface ChartsCountMapper {

	public List<Object> selectChartsCountInfoByTime(@Param("startDates")String startDates, @Param("endDates")String endDates,
			@Param("tspan")int tspan,@Param("equipmentids")List<Long> equipmentids);

	
}
