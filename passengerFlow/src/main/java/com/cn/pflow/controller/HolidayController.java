package com.cn.pflow.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cn.pflow.domain.HolidayResults;
import com.cn.pflow.listener.SpringContextListener;
import com.cn.pflow.service.HolidayService;
import com.cn.pflow.service.IUserService;
import com.cn.pflow.util.HolidayApi;

@Controller
@RequestMapping("/holiday")
public class HolidayController {
	
	@Resource
	private HolidayService holidayService;
	
	/**
	 * 新增假日
	 * @return
	 */
	@RequestMapping(value="insert",method=RequestMethod.POST)
	public void saveHoliday(){
		List<HolidayResults> selectList = this.getHoliday();//数据表中的节假日
		List<HolidayResults> apiList = HolidayApi.getHolidayResults();//API查询出来的节假日
//		System.out.println("表中节假日"+selectList);
//		System.out.println("表中节假日长度"+selectList.size());
//		System.out.println("API节假日"+apiList);
		
		try {
			if("".equals(apiList) || null==apiList){
				return;
			}else{
				if(selectList.size() == 0){//如果数据表中没有节假日，直接插入
					for (HolidayResults apiResult : apiList){
						holidayService.insertHoliday(apiResult);
					}
				}else{//如果数据表中有节假日，把查询出来的节假日和数据表中的节假日对比去重后插入
//					System.out.println("API节假日长度"+apiList.size());
					for (int i = 0; i < apiList.size(); i++) {  
					    HolidayResults api=apiList.get(i);  
					    for (int j = 0; j < selectList.size(); j++) {  
					        HolidayResults select=selectList.get(j);  
					        if ((api.getDate()).equals((select.getDate()))) {  
					            apiList.remove(i); 
					            i--;
					            break;
					        }  
					    }
					} 
//					System.out.println("API节假日去重后长度"+apiList.size());
					for (HolidayResults apiResult : apiList) {
						holidayService.insertHoliday(apiResult);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取节假日
	 * @return
	 */
	@RequestMapping(value="selectHoliday",method=RequestMethod.GET)
	public List<HolidayResults> getHoliday(){
		List<HolidayResults> selectList = holidayService.getHoliday();
		return selectList;
	}
	
}


