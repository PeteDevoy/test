package com.cn.pflow.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.cn.pflow.IDao.PflowconfigMapper;
import com.cn.pflow.domain.Pflowconfig;
import com.cn.pflow.domain.ResultStatus;
import com.cn.pflow.service.IPfconfigService;

/**
 * 客流配置信息设置实现类
 * @author james
 *
 */

@Service("iPfconfigService")
public class PfconfigServiceImpl implements IPfconfigService {

	@Resource
	private PflowconfigMapper pflowconfigDao;
	

	/**
	 * 设置配置信息
	 * 
	 * @param sceneMode 场景模式
	 * @param pfStartTime 统计起始时间
	 * 
	 */
	
	@Override
	public String setConfigInfo(String sceneMode, String pfStartTime) {
		// TODO Auto-generated method stub
		
		Pflowconfig pflowconfig = new Pflowconfig();
		ResultStatus resultStatus = new ResultStatus();
		
		pflowconfig.setId(1);
		pflowconfig.setScenemode(Integer.parseInt(sceneMode));
		pflowconfig.setStarttime(pfStartTime);
		
		if(getPflowconfig() != null){
			
			if(this.pflowconfigDao.updateByPrimaryKey(pflowconfig) > 0){
				resultStatus.setStatus("0");
				resultStatus.setSuccess(true);
				return JSON.toJSONString(resultStatus);
				
			}else{
				resultStatus.setStatus("1");
				resultStatus.setSuccess(false);
				return JSON.toJSONString(resultStatus);
			}
			
		}else{
			
			if(this.pflowconfigDao.insert(pflowconfig) > 0){
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

	//获取配置信息
	private Pflowconfig getPflowconfig() {
		// TODO Auto-generated method stub
		return this.pflowconfigDao.selectByPrimaryKey(1);
		
	}

	//返回配置信息
	@Override
	public String getConfigInfo() {
		// TODO Auto-generated method stub
		
		Pflowconfig pflowconfig = getPflowconfig();
		ResultStatus resultStatus = new ResultStatus();
		
		if(pflowconfig != null){
			resultStatus.setStatus("0");
			resultStatus.setSuccess(true);
			resultStatus.setData(pflowconfig);	
		}else{
			resultStatus.setStatus("1");
			resultStatus.setSuccess(false);
		}
		return JSON.toJSONString(resultStatus);
		
	}



}
