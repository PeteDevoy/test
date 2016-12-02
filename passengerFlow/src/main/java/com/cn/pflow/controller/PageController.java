package com.cn.pflow.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.pflow.scheduler.jobs.HolidayScheduledJob;
import com.cn.pflow.service.PageService;

@Controller
@RequestMapping("/page")
public class PageController {
	@Resource
	private PageService pageService;
	
	/**
	 * 通用的页面跳转的方法 
	 */
	@RequestMapping(value="{pageName}",method=RequestMethod.GET)
	public String toPage(@PathVariable("pageName")String pageName){
		return pageName;
	}
	
	/**
	 * 获取进出数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/io")
	@ResponseBody
	public Map<String,Object> getIo(HttpServletRequest request,Model model){
		String startDates =request.getParameter("startDate");
		String endDates =request.getParameter("endDate");
		String timespan =request.getParameter("timespan");
		Long area = Long.valueOf(request.getParameter("area"));
		//Long area = (long) 1;
		
		Map<String, Object> charts = pageService.getIo(startDates,endDates,timespan,area);
		return charts;
	}
	
	/**
	 * 获取时锋和滞留
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/iosumdiff")
	@ResponseBody
	public Map<String,Object> getiosumdiff(HttpServletRequest request,Model model){
		String startDates =request.getParameter("startDate");//前台传入起始时间
		String endDates =request.getParameter("endDate");//前台传入结束时间
		String timespan =request.getParameter("timespan");//前台传入时间间隔
		String warnLine = request.getParameter("warnLine");// 获取警戒线参数
		String scene = request.getParameter("scene");// 获取场景参数
		Long area = Long.valueOf(request.getParameter("area"));
		
		Map<String, Object> charts = pageService.getiosumdiff(startDates,endDates,timespan,warnLine,scene,area);
		return charts;
	}
	
	/**
	 * 地图点开进出表（只读）
	 * @param request
	 * @param model
	 * @return
	 */
	/*@RequestMapping("/ioreadonly")
	@ResponseBody
	public Map<String,Object> getioreadonly(HttpServletRequest request,Model model){
		String startDates =request.getParameter("startDate");
		String endDates =request.getParameter("endDate");
		String timespan =request.getParameter("timespan");
		
		Map<String, Object> charts = pageService.getioreadonly(startDates,endDates,timespan);
		return charts;
	}*/
	
	/**
	 * 地图点开时锋表（只读）
	 * @param request
	 * @param model
	 * @return
	 */
	/*@RequestMapping("/iosumreadonly")
	@ResponseBody
	public Map<String,Object> getiosumreadonly(HttpServletRequest request,Model model){
		String startDates =request.getParameter("startDate");//前台传入起始时间
		String endDates =request.getParameter("endDate");//前台传入结束时间
		String timespan =request.getParameter("timespan");//前台传入时间间隔
		
		Map<String, Object> charts = pageService.getiosumreadonly(startDates,endDates,timespan);
		return charts;
	}*/
	
	/**
	 * 地图点开滞留表（只读）
	 * @param request
	 * @param model
	 * @return
	 */
	/*@RequestMapping("/iodiffreadonly")
	@ResponseBody
	public Map<String,Object> getiodiffreadonly(HttpServletRequest request,Model model){
		String startDates =request.getParameter("startDate");//前台传入起始时间
		String endDates =request.getParameter("endDate");//前台传入结束时间
		String timespan =request.getParameter("timespan");//前台传入时间间隔
		
		Map<String, Object> charts = pageService.getiodiffreadonly(startDates,endDates,timespan);
		return charts;
	}*/
}
