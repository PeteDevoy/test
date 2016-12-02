package com.cn.pflow.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.pflow.IDao.HolidayMapper;
import com.cn.pflow.domain.HolidayResults;
import com.cn.pflow.service.HolidayService;

@Service("holidayService")
public class HolidayServiceImpl implements HolidayService {
	
	@Resource
	private HolidayMapper holidayDao;
	
	/**
	 * 保存节假日
	 */
	@Override
	public void insertHoliday(HolidayResults result) {
		
		holidayDao.insertHoliday(result);
	}
	
	/**
	 * 获取节假日
	 */
	@Override
	public List<HolidayResults> getHoliday() {
		return holidayDao.getHoliday();
	}

}
