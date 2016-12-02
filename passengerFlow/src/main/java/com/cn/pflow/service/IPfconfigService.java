package com.cn.pflow.service;

import javax.jws.WebService;

/**
 * 客流配置设置接口
 * @author james
 *
 */

@WebService
public interface IPfconfigService {

	//保存配置信息
	public String setConfigInfo(String sceneMode, String pfStartTime);
	
	//获取配置信息
	public String getConfigInfo();
}
