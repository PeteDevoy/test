package com.cn.pflow.service;

import java.util.List;

import com.cn.pflow.domain.Pflowcount;
import com.cn.pflow.domain.PflowcountSelect;
import com.cn.pflow.domain.Useralarmlog;
import com.cn.pflow.domain.Alarminfo;

/**
 * 操作设备数据
 * @author james
 *
 */

public interface IPfcountService {

	//添加设备数据
	public boolean addPfcountInfo(Pflowcount pflowcount);
	
	
	
	//返回数据List
	public List<?> countInfoByTime(PflowcountSelect example);
	
	/*
	 * 查询未检索记录
	 * @param _id已检索最大id
	 * @return List<Pflowcount>
	 */
	public List<Pflowcount> GetPfCountAfterId(long _id);
	
	/*
	 * 查询%d分钟前的数据
	 * @param 
	 * @return List<Pflowcount>
	 */
	public List<Pflowcount> GetPfCountFewMinutesBefore(long _id, String _mac, int iMinute);
	
	/*
	 * 查询最大索引号
	 * @param
	 * @return max id
	 */
	public long GetMaxIdxFromPfCount();
	
	/*
	 * 高通过量告警
	 * @param
	 * @return
	 */
	public int AddHighthroughAlarm2ual(Useralarmlog _info);
	
	/*
	 * 高通过量告警
	 * @param
	 * @return
	 */
	public int AddHighthroughAlarm2ai(Alarminfo _info);
}
