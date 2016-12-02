package com.cn.pflow.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cn.pflow.IDao.PflowalarmconfigMapper;
import com.cn.pflow.domain.Pflowalarmconfig;
import com.cn.pflow.domain.ResultStatus;
import com.cn.pflow.service.IAlarmConfigService;


/**
 * 告警配置信息设置实现类
 * @author james
 *
 */

@Service("alarmconfigService")
public class AlarmconfigServiceImpl implements IAlarmConfigService {

	@Resource
	private PflowalarmconfigMapper alarmConfigDao;
	

	/**
	 * 设置告警配置信息
	 * 
	 * @param basicpeak 基础阀值
	 * @param timeinterval 时段
	 * @param peak 阀值
	 * @return 状态 
	 */

	@Override
	public String setAlarmConfigInfo(String basicpeak, String timeinterval, String peak) {
		// TODO Auto-generated method stub
		
		Pflowalarmconfig AlarmConfig = new Pflowalarmconfig();
		ResultStatus resultStatus = new ResultStatus();
		
		AlarmConfig.setId(1);
		AlarmConfig.setBasicpeak(Integer.parseInt(basicpeak));
		AlarmConfig.setTimeinterval(Integer.parseInt(timeinterval));
		AlarmConfig.setPeak(Integer.parseInt(peak));
		
		if(getAlarmConfig() != null){
			
			if(this.alarmConfigDao.updateByPrimaryKey(AlarmConfig) > 0){
				resultStatus.setStatus("0");
				resultStatus.setSuccess(true);
				return JSON.toJSONString(resultStatus);
				
			}else{
				resultStatus.setStatus("1");
				resultStatus.setSuccess(false);
				return JSON.toJSONString(resultStatus);
			}
			
		}else{
			
			if(this.alarmConfigDao.insert(AlarmConfig) > 0){
				resultStatus.setStatus("0");
				resultStatus.setSuccess(true);
				return JSON.toJSONString(resultStatus);
				
			}else{
				resultStatus.setStatus("1");
				resultStatus.setSuccess(false);
				return JSON.toJSONString(resultStatus);
			}

		}
		
		
	}
	
	

	/**
	 * 获取告警相关配置信息
	 * 
	 * @return 返回设备信息json
	 * 
	 */
	
	@Override
	public String getAlarmConfigInfo() {
		// TODO Auto-generated method stub
		
		Pflowalarmconfig alarmConfig = getAlarmConfig();
		ResultStatus resultStatus = new ResultStatus();
		
		if(alarmConfig != null){
			return JSON.toJSONString(alarmConfig);
		}else{
			resultStatus.setStatus("1");
			resultStatus.setSuccess(false);
			return JSON.toJSONString(resultStatus);
		}

	}

	
	//获取告警配置信息
	public Pflowalarmconfig getAlarmConfig() 
	{
		// TODO Auto-generated method stub
		return this.alarmConfigDao.selectByPrimaryKey(1);
		
	}


}
