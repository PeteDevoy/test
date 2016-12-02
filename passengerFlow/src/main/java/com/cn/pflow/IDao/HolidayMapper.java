package com.cn.pflow.IDao;

import java.util.List;

import com.cn.pflow.domain.HolidayResults;

public interface HolidayMapper {
	
	/**
	 * 保存节假日
	 * @param result
	 * @return
	 */
	public void insertHoliday(HolidayResults result);
	
	/**
	 * 获取节假日
	 */
	public List<HolidayResults> getHoliday();

}
