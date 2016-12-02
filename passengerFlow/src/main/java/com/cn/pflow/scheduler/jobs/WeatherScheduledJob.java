package com.cn.pflow.scheduler.jobs;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cn.pflow.IDao.WeatherMapper;
import com.cn.pflow.domain.WeatherInf;
import com.cn.pflow.listener.SpringContextListener;
import com.cn.pflow.service.IPfcountService;
import com.cn.pflow.service.WeatherService;
import com.cn.pflow.util.getWeatherInform;
import com.cn.pflow.util.resolveWeatherInf;




public class WeatherScheduledJob extends QuartzJobBean {  
	
	
	WeatherService weatherService = (WeatherService) SpringContextListener.getApplicationContext().getBean(WeatherService.class);

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("天气查询开始啦！！！！！");
		int count =0;
		WeatherInf weatherInf=new WeatherInf();
		String xml=getWeatherInform.getWeatherInform("上海");
		weatherInf=resolveWeatherInf.resolveWeatherInf(xml);
		count=weatherService.countweather(weatherInf.getWeatherInfs().getDate());
		if(count==0){
		weatherService.inweather(weatherInf);
		}else{
			return;
		} 
	}  
}  
