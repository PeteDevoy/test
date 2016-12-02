package com.cn.pflow.scheduler.jobs;



import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cn.pflow.controller.HolidayController;
import com.cn.pflow.listener.SpringContextListener;
import com.cn.pflow.service.HolidayService;


public class HolidayScheduledJob extends QuartzJobBean {  
	
	HolidayService holidayService = (HolidayService) SpringContextListener.getApplicationContext().getBean(HolidayService.class);
	HolidayController holidayController = (HolidayController) SpringContextListener.getApplicationContext().getBean(HolidayController.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		holidayController.saveHoliday();
		System.out.println("节假日定时任务");
	} 
	
}