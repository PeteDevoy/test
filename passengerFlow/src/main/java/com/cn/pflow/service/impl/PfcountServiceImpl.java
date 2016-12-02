package com.cn.pflow.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.pflow.IDao.PflowcountMapper;
import com.cn.pflow.domain.Alarminfo;
import com.cn.pflow.domain.Pflowcount;
import com.cn.pflow.domain.PflowcountSelect;
import com.cn.pflow.domain.Useralarmlog;
import com.cn.pflow.service.IPfcountService;
import com.cn.pflow.IDao.AlarminfoMapper;
import com.cn.pflow.IDao.UseralarmlogMapper;

/**
 * 设备数据操作类
 * @author james
 *
 */
@Service("pfcountService")
public class PfcountServiceImpl implements IPfcountService  {
	
	@Resource
	private PflowcountMapper pfcountDao;
	@Resource
	private AlarminfoMapper pfAlarmInfoDao;
	@Resource
	private UseralarmlogMapper pfUsrAlarmLogDao;
	
	
	/**
	 * 插入设备数据
	 * @param 数据pojo
	 * @return 是否成功
	 */
	@Override
	public boolean addPfcountInfo(Pflowcount pflowcount) {
		// TODO Auto-generated method stub
		
		if(this.pfcountDao.insert(pflowcount) > 0){
			return true;
		}else{
			return false;
		}
	}

	
	/**
	 * 查询记录数据
	 * @param 构造where语句
	 * @return 返回数据的List
	 */
	
	@Override
	public List<?> countInfoByTime(PflowcountSelect select) {
		// TODO Auto-generated method stub
		
		return this.pfcountDao.selectByExample(select);
	}


	/*
	 * 查询未检索记录
	 * @param _id已检索最大id
	 * @return List<Pflowcount>
	 */
	@Override
	public List<Pflowcount> GetPfCountAfterId(long _id) {
		// TODO Auto-generated method stub
		return this.pfcountDao.selectPfCountAfterId(_id);
	}


	/*
	 * 查询最大索引号
	 * @param
	 * @return max id
	 */
	@Override
	public long GetMaxIdxFromPfCount() {
		// TODO Auto-generated method stub
		return this.pfcountDao.selectMaxIdx();
	}


	/*
	 * 高通过量告警
	 * @param
	 * @return
	 */
	@Override
	public int AddHighthroughAlarm2ual(Useralarmlog _info) {
		// TODO Auto-generated method stub
		return this.pfUsrAlarmLogDao.insertSelective(_info);
	}


	/*
	 * 高通过量告警
	 * @param
	 * @return
	 */
	@Override
	public int AddHighthroughAlarm2ai(Alarminfo _info) {
		// TODO Auto-generated method stub
		return this.pfAlarmInfoDao.insertSelective(_info);
	}


	/*
	 * 查询%d分钟前的数据
	 * @param 
	 * @return List<Pflowcount>
	 */
	@Override
	public List<Pflowcount> GetPfCountFewMinutesBefore(long _id, String _mac, int iMinute) {
		// TODO Auto-generated method stub
		return this.pfcountDao.selectPfCountFewMinutesBefore(_id, _mac, 0, iMinute);
	}

}
