package com.cn.pflow.service;

import java.util.List;

import com.cn.pflow.domain.HolidayResults;

public interface HolidayService {
	
	/**
	 * 保存节假日
	 * @param result
	 * @return
	 */
	public void insertHoliday(HolidayResults result);
	
	/**
	 * 获取节假日
	 * @return
	 */
	public List<HolidayResults> getHoliday();

}
