package com.cn.pflow.service;

import javax.jws.WebService;
import com.cn.pflow.domain.Pflowalarmconfig;

/**
 * 告警配置信息接口
 * @author james
 *
 */

@WebService
public interface IAlarmConfigService {

	//保存告警配置信息
	public String setAlarmConfigInfo(String basicpeak, String timeinterval, String peak);
	
	//获取告警配置信息
	public String getAlarmConfigInfo();
	
	//获取告警配置信息
	public Pflowalarmconfig getAlarmConfig();
}
