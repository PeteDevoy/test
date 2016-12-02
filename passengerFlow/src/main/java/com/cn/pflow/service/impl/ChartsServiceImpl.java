package com.cn.pflow.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.pflow.IDao.ChartsCountMapper;
import com.cn.pflow.IDao.ChartsServiceMapper;
import com.cn.pflow.IDao.PflowcountMapper;
import com.cn.pflow.domain.Charts;
import com.cn.pflow.domain.ChartsCount;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.pflow.domain.Pflowcount;
import com.cn.pflow.domain.PflowcountSelect;
import com.cn.pflow.domain.PflowcountSelect.Criteria;
import com.cn.pflow.service.IChartsService;
import com.cn.pflow.service.IPfconfigService;
import com.cn.pflow.service.IPfcountService;
import com.cn.pflow.util.StringUtil;

/**
 * 鎶ヨ〃鏁版嵁瀹炵幇绫�
 * 
 *
 */
@Service("chartsService")
public class ChartsServiceImpl implements IChartsService {

	
	@Resource
	private IPfcountService pfcountService;
	
	@Resource
	private IPfconfigService pflowconfigService;
	
	@Resource
	private ChartsCountMapper chartsCountMapper;
	
	@Resource
	private ChartsServiceMapper chartsServiceDao;
	
	/**
	 * 鑾峰彇褰撴棩鎬昏繘鍏ユ暟
	 * @return 杩斿洖鏁伴噺
	 */
	
	@Override
	public String getTotalEntersCount() {
		// TODO Auto-generated method stub
		
		String startDate = StringUtil.Date2String(new Date());
		String endDate = StringUtil.Date2String(new Date());
		
		String startTime;
		
		String configJson = pflowconfigService.getConfigInfo();
		JSONObject resultStatus = JSON.parseObject(configJson);
		
		if(resultStatus.get("data") != null){
			startTime = JSON.parseObject(resultStatus.get("data").toString()).get("starttime").toString();
		}else{
			startTime = "00:00:00";
		}
		
		String endTime = StringUtil.Time2String(new Date());
		
		List<Pflowcount> countInfoList = getPflowCountInfoByTime(startDate, endDate, startTime, endTime);
		
		int enterCount = 0;
		
		for(int i=0;i<countInfoList.size();i++){
			enterCount += countInfoList.get(i).getEnters();
		}
		
		return String.valueOf(enterCount);
		
	}
	
	


	@SuppressWarnings("unchecked")
	public List<Pflowcount> getPflowCountInfoByTime(String startDate, String endDate, String startTime, String endTime) {
	
		PflowcountSelect pflowcount = new PflowcountSelect();
	
		Criteria criteria = pflowcount.createCriteria();
		criteria.andEnddateBetween(StringUtil.String2Date(startDate), StringUtil.String2Date(endDate));
		criteria.andEndtimeBetween(StringUtil.String2Time(startTime), StringUtil.String2Time(endTime));

		return (List<Pflowcount>) this.pfcountService.countInfoByTime(pflowcount);
	}




	@Override
	public List<Object> getChartsCountInfoByTime(String startDates, String endDates,int tspan,List<Long> equipmentids) {
		return chartsCountMapper.selectChartsCountInfoByTime(startDates,endDates,tspan,equipmentids);
	}
	
	@Override
	public List<Object> getSeventyChartsInfoByTime(String startDate,String endDate, String startTime, String endTime,List<Long> equipmentids) {
		// TODO Auto-generated method stub
		return this.chartsServiceDao.getSeventyChartsInfoByTime(startDate,endDate,startTime,endTime,equipmentids);
	}

	@Override
	public List<Object> getEightyChartsInfoByTime(String startDate,String endDate, String startTime, String endTime,List<Long> equipmentids) {
		// TODO Auto-generated method stub
		return this.chartsServiceDao.getEightyChartsInfoByTime(startDate,endDate,startTime,endTime,equipmentids);
	}
	@Override
	public List<Object> getNinetyChartsInfoByTime(String startDate,String endDate, String startTime, String endTime,List<Long> equipmentids) {
		// TODO Auto-generated method stub
		return this.chartsServiceDao.getNinetyChartsInfoByTime(startDate,endDate,startTime,endTime,equipmentids);
	}


}
