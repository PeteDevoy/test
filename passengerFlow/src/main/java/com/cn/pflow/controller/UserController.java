package com.cn.pflow.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.pflow.domain.ChartsCount;
import com.cn.pflow.domain.Pflowcount;
import com.cn.pflow.domain.Charts;
import com.cn.pflow.domain.User;
import com.cn.pflow.service.IChartsService;
import com.cn.pflow.service.IPfcountService;
import com.cn.pflow.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private IUserService userService;
	
	@Resource
	private IPfcountService ipfcountService;
	
	@Resource
	private IChartsService iChartsService;
	
	@RequestMapping("/show")
	@ResponseBody
	public Map<String,Object> toShow(HttpServletRequest request,Model model){
		Logger logger = Logger.getLogger(UserController.class);
		
		String startDates =request.getParameter("startDate");
		String endDates =request.getParameter("endDate");
		String timespan =request.getParameter("timespan");
		
		String initDate = UserController.toString(new Date(), "yyyy-MM-dd 00:00:00");//获取当前天零点时间
		
		if(startDates==null||startDates.isEmpty()){
			 startDates=initDate;
		}
		String[] startDatearray = startDates.split(" ");//截取时间
		String startDate=startDatearray[0];
		String startTime = startDatearray[1];//得到时间
		
		if(endDates==null||endDates.isEmpty()){
			 endDates=UserController.toString(new Date(), "yyyy-MM-dd kk:mm:00");//获取当前时间
		}
		String[] endDatearray = endDates.split(" ");//截取时间
		String endDate = endDatearray[0];//得到时间
		String endTime = endDatearray[1];//得到时间
		
		List<Object> line=null;
		String[] legend=null;
		int[] series=null;
		int[] series1=null;
		int datespan=0;//日期差
		int totalin=0;
		int totalout=0;
		

		//判断时间跨度
		if(timespan == null || timespan.equals("")){
			/*line = iChartsService.getEightyChartsInfoByTime(startDate, endDate,startTime, endTime);
			legend = new String[line.size()];
			series = new int[line.size()];
			series1 = new int[line.size()];
			
			for(int i=0;i<line.size();i++){
				legend[i]=((Charts) line.get(i)).getStartdate();
				series[i]=((Charts)line.get(i)).getSumenters();
				series1[i]=((Charts)line.get(i)).getSumexits();
				totalin+=series[i];
				totalout+=series1[i];
			}*/
			int tspan = 1;
			//line=iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan);//查出的数组
			legend=new String[line.size()];//x轴，时间
			series=new int[line.size()];// y轴，数据1，入
			series1=new int[line.size()];// y轴，数据2，出
			
			for(int i = 0; i < line.size(); i++){
				legend[i] = ((ChartsCount) line.get(i)).getEnddatetime();
				series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;
				series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;
				totalin+=((ChartsCount) line.get(i)).getSumenters();
				totalout+=((ChartsCount) line.get(i)).getSumexits();
			}
		}else if(Integer.valueOf(timespan) > 69){
			if (timespan.equals("70")) {
				//line = iChartsService.getSeventyChartsInfoByTime(startDate, endDate,startTime, endTime);
				
			}else if (timespan.equals("80")) {
				//line = iChartsService.getEightyChartsInfoByTime(startDate, endDate,startTime, endTime);
			
			}else if (timespan.equals("90")) {
				//line = iChartsService.getNinetyChartsInfoByTime(startDate, endDate,startTime, endTime);
				
			}  
			
			legend = new String[line.size()];
			series = new int[line.size()];
			series1 = new int[line.size()];
			
			for(int i=0;i<line.size();i++){
				legend[i]=((Charts) line.get(i)).getStartdate();
				series[i]=((Charts)line.get(i)).getSumenters();
				series1[i]=((Charts)line.get(i)).getSumexits();
				totalin+=series[i];
				totalout+=series1[i];
			}
			
		} else if(Integer.valueOf(timespan) < 69){
			int tspan = Integer.valueOf(timespan);
			//line=iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan);//查出的数组
			legend=new String[line.size()];//x轴，时间
			series=new int[line.size()];// y轴，数据1，入
			series1=new int[line.size()];// y轴，数据2，出
			
			for(int i = 0; i < line.size(); i++){
				legend[i] = ((ChartsCount) line.get(i)).getEnddatetime();
				series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;
				series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;
				totalin+=((ChartsCount) line.get(i)).getSumenters();
				totalout+=((ChartsCount) line.get(i)).getSumexits();
			}
		}

		Map<String, Object> charts=new HashMap<>();
		charts.put("totalin", totalin);
		charts.put("totalout", totalout);		
		charts.put("legend", legend);
		charts.put("series", series);
		charts.put("series1", series1);
		return charts;

	}
	
	
	@RequestMapping("/showDouble")
	@ResponseBody
	public Map<String,Object> toShowDouble(HttpServletRequest request,Model model){
		Logger logger = Logger.getLogger(UserController.class);
		
		String startDates =request.getParameter("startDate");//前台传入起始时间
		String endDates =request.getParameter("endDate");//前台传入结束时间
		String timespan =request.getParameter("timespan");//前台传入时间间隔
		String warnLine = request.getParameter("warnLine");// 获取警戒线参数
		String scene = request.getParameter("scene");// 获取场景参数
		
		String initDate = UserController.toString(new Date(), "yyyy-MM-dd 00:00:00");//获取当前天零点时间
		
		if(startDates==null||startDates.isEmpty()){
			 startDates=initDate;
		}
		String[] startDatearray = startDates.split(" ");//截取时间
		String startDate=startDatearray[0];//得到日期
		String startTime = startDatearray[1];//得到时间
		
		if(endDates==null||endDates.isEmpty()){
			 endDates=UserController.toString(new Date(), "yyyy-MM-dd kk:mm:00");//获取当前时间
		}
		String[] endDatearray = endDates.split(" ");//截取时间
		String endDate = endDatearray[0];//得到日期
		String endTime = endDatearray[1];//得到时间
		
		List<Object> line=null;
		String[] legendDouble=null;
		int[] series=null;
		int[] series1=null;
		int[] seriesDouble =null;
		int datespan=0;//日期差
		int totalin=0;//总进	
		int totalout=0;//总出
		int total=0;
		
		//判断时间跨度
		if(timespan == null || timespan.equals("")){
			int tspan = 1;
//			line = iChartsService.getEightyChartsInfoByTime(startDate, endDate,startTime, endTime);
			//line = iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan);
			/*legendDouble = new String[line.size()];
			series = new int[line.size()];
			series1 = new int[line.size()];
			seriesDouble = new int[line.size()];
			
			for(int i=0;i<line.size();i++){
				legendDouble[i]=((Charts) line.get(i)).getStartdate();
				series[i]=((Charts)line.get(i)).getSumenters();
				series1[i]=((Charts)line.get(i)).getSumexits();
				totalin+=series[i];
				totalout+=series1[i];
				if(scene == null || scene.equals("") || scene.equals("1")){
					seriesDouble[i]=series[i]+series1[i];//时锋
					total=totalin+totalout;
				} else{
					seriesDouble[i]=series[i]-series1[i];//滞留
					total=totalin-totalout;
				}
				
			}*/
			legendDouble=new String[line.size()];//x轴，时间
			series=new int[line.size()];// y轴，数据1，入
			series1=new int[line.size()];// y轴，数据2，出
			seriesDouble = new int[line.size()];
			
			for(int i = 0; i < line.size(); i++){
				legendDouble[i] = ((ChartsCount) line.get(i)).getEnddatetime();
				series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;
				series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;
				totalin+=((ChartsCount) line.get(i)).getSumenters();
				totalout+=((ChartsCount) line.get(i)).getSumexits();
				if(scene == null || scene.equals("") || scene.equals("1")){
					seriesDouble[i]=series[i]+series1[i];//时锋
					total=totalin+totalout;
				} else{
					seriesDouble[i]=series[i]-series1[i];//滞留
					total=totalin-totalout;
				}
			}
		}else if(Integer.valueOf(timespan) > 69){
			if (timespan.equals("70")) {
				//line = iChartsService.getSeventyChartsInfoByTime(startDate, endDate,startTime, endTime);
				
			}else if (timespan.equals("80")) {
				//line = iChartsService.getEightyChartsInfoByTime(startDate, endDate,startTime, endTime);
			
			}else if (timespan.equals("90")) {
				//line = iChartsService.getNinetyChartsInfoByTime(startDate, endDate,startTime, endTime);
				
			}  
			
			legendDouble = new String[line.size()];
			series = new int[line.size()];
			series1 = new int[line.size()];
			seriesDouble = new int[line.size()];
			
			for(int i=0;i<line.size();i++){
				legendDouble[i]=((Charts) line.get(i)).getStartdate();
				series[i]=((Charts)line.get(i)).getSumenters();
				series1[i]=((Charts)line.get(i)).getSumexits();
				totalin+=series[i];
				totalout+=series1[i];
				if(scene == null || scene.equals("") || scene.equals("1")){
					seriesDouble[i]=series[i]+series1[i];//时锋
					total=totalin+totalout;
				} else{
					seriesDouble[i]=series[i]-series1[i];//滞留
					total=totalin-totalout;
				}
			}
			
		} else if(Integer.valueOf(timespan) < 69){
			int tspan = Integer.valueOf(timespan);
			//line=iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan);//查出的数组
			legendDouble=new String[line.size()];//x轴，时间
			series=new int[line.size()];// y轴，数据1，入
			series1=new int[line.size()];// y轴，数据2，出
			seriesDouble = new int[line.size()];
			
			for(int i = 0; i < line.size(); i++){
				legendDouble[i] = ((ChartsCount) line.get(i)).getEnddatetime();
				series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;
				series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;
				totalin+=((ChartsCount) line.get(i)).getSumenters();
				totalout+=((ChartsCount) line.get(i)).getSumexits();
				if(scene == null || scene.equals("") || scene.equals("1")){
					seriesDouble[i]=series[i]+series1[i];//时锋
					total=totalin+totalout;
				} else{
					seriesDouble[i]=series[i]-series1[i];//滞留
					total=totalin-totalout;
				}
			}
		}
		
//		String startDate =request.getParameter("startDate");//前台传入起始时间
//		String endDate =request.getParameter("endDate");//前台传入结束时间
//		String timespan =request.getParameter("timespan");//前台传入时间间隔
//		String warnLine =request.getParameter("warnLine");//获取警戒线参数
//		String scene =request.getParameter("scene");//获取场景参数
//		
//		String initDate = UserController.toString(new Date(), "yyyy-MM-dd 00:00:00");//获取当前天零点时间
//		
//		if(startDate==null||startDate.isEmpty()){
//			 startDate=initDate;
//		}
//		if(endDate==null||endDate.isEmpty()){
//			 endDate=UserController.toString(new Date(), "yyyy-MM-dd kk:mm:00");//获取当前时间
//		}
//		
//		int tspan=0;
//		if(timespan==null||timespan.equals("")){
//			tspan=15;
//		}else{
//			tspan=Integer.valueOf(timespan);//获取当前时间间隔
//		}
//		
//		List<Object> line=null;
//		String[] legendDouble=null;
//		int[] series=null;
//		int[] series1=null;
//		int[] seriesDouble=null;
//		int totalin=0;
//		int totalout=0;
//		int total=0;
//		
//		try {
//			line=iChartsService.getChartsCountInfoByTime(startDate, endDate,tspan);//查出的集合
//			legendDouble=new String[line.size()];//x轴，时间
//			series=new int[line.size()];// y轴，数据1，入
//			series1=new int[line.size()];// y轴，数据2，出
//			seriesDouble=new int[line.size()]; //时锋数据or滞留数据
//			for(int i = 0; i < line.size(); i++){
//				legendDouble[i] = ((ChartsCount) line.get(i)).getEnddatetime();
//				series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;
//				series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;
//				totalin+=((ChartsCount) line.get(i)).getSumenters();
//				totalout+=((ChartsCount) line.get(i)).getSumexits();
//				if(scene==null || scene.equals("") || scene.equals("1")){
//					seriesDouble[i]=series[i]+series1[i];//时峰
//					total=totalin+totalout;
//				}else{
//					seriesDouble[i]=series[i]-series1[i];//滞留
//					total=totalin-totalout;
//				}
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
		int markLine;
		if(warnLine == null || warnLine.equals("")){
			markLine=(int) ((UserController.getMax(seriesDouble))*0.9);//默认警戒值
		}else{
			markLine = Integer.valueOf(warnLine);//前台设置的警戒值
		}
		
		Map<String, Object> charts=new HashMap<>();
		charts.put("legendDouble", legendDouble);//X轴
		charts.put("seriesDouble", seriesDouble);//Y轴
		charts.put("markLineDouble", markLine);//警戒线值
		charts.put("total", total);//总量
		return charts;
	}
	
	@RequestMapping("/search")
	public String toSearch(HttpServletRequest request,Model model){
		String startDate =request.getParameter("startDate");
		String endDate =request.getParameter("endDate");
		String timespan =request.getParameter("timespan");
		

		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(timespan);
//		User user = this.userService.getUserById(userId);
//		model.addAttribute("user", user);
		return "asd";
	}
	
	
	@RequestMapping(value="/dwr")
	public String dwr(HttpServletRequest request,Model model){
		return "dwr";
	}
	
	@RequestMapping(value="/realtime")
	public String realtime(HttpServletRequest request,Model model){
		return "realtime";
	}
	
	@RequestMapping("/showName")
	public String showName(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showName";
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String showSpitterProfile(@PathVariable String id, Model model) {

		int userId = Integer.parseInt(id);
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "name";

	}
	
	@RequestMapping(value="/test")
	public String test(HttpServletRequest request,Model model){
		return "index";
	}
	
	@RequestMapping(value="/weather")
	public String test2(HttpServletRequest request,Model model){
		return "weather";
	}
	
	@RequestMapping(value="/index2")
	public String test3(HttpServletRequest request,Model model){
		return "index2";
	}
	
	@RequestMapping(value="/enterExit")
	public String test4(HttpServletRequest request,Model model){
		return "enterExit";
	}
	
	
	public static String toString(Date date, String formaterString) {  
	    String time;  
	    SimpleDateFormat formater = new SimpleDateFormat();  
	    formater.applyPattern(formaterString);  
	    time = formater.format(date);  
	    return time;  
	}
	
	
	public static int getMax(int[] arr){
        int max=0;
        for(int i=0;arr!=null&&i<arr.length;i++){
           if(arr[i]>max){
                max=arr[i];
            }
        }
        return max;
   }
	

@RequestMapping("/show2")
@ResponseBody
public Map<String,Object> toShow2(HttpServletRequest request,Model model){
	Logger logger = Logger.getLogger(UserController.class);
	
	String startDates =request.getParameter("startDate");
	String endDates =request.getParameter("endDate");
	String timespan =request.getParameter("timespan");
	
	String initDate = UserController.toString(new Date(), "yyyy-MM-dd 00:00:00");//获取当前天零点时间
	
	if(startDates==null||startDates.isEmpty()){
		 startDates=initDate;
	}
	String[] startDatearray = startDates.split(" ");//截取时间
	String startDate=startDatearray[0];
	String startTime = startDatearray[1];//得到时间
	
	if(endDates==null||endDates.isEmpty()){
		 endDates=UserController.toString(new Date(), "yyyy-MM-dd kk:mm:00");//获取当前时间
	}
	String[] endDatearray = endDates.split(" ");//截取时间
	String endDate = endDatearray[0];//得到时间
	String endTime = endDatearray[1];//得到时间
	
	List<Object> line=null;
	String[] legend=null;
	int[] series=null;
	int[] series1=null;
	int datespan=0;//日期差
	int totalin=0;
	int totalout=0;
	

	//判断时间跨度
	if(timespan == null || timespan.equals("")){
		/*line = iChartsService.getEightyChartsInfoByTime(startDate, endDate,startTime, endTime);
		legend = new String[line.size()];
		series = new int[line.size()];
		series1 = new int[line.size()];
		
		for(int i=0;i<line.size();i++){
			legend[i]=((Charts) line.get(i)).getStartdate();
			series[i]=((Charts)line.get(i)).getSumenters();
			series1[i]=((Charts)line.get(i)).getSumexits();
			totalin+=series[i];
			totalout+=series1[i];
		}*/
		int tspan = 60;
		//line=iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan);//查出的数组
		legend=new String[line.size()];//x轴，时间
		series=new int[line.size()];// y轴，数据1，入
		series1=new int[line.size()];// y轴，数据2，出
		
		for(int i = 0; i < line.size(); i++){
			legend[i] = ((ChartsCount) line.get(i)).getEnddatetime();
			series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;
			series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;
			totalin+=((ChartsCount) line.get(i)).getSumenters();
			totalout+=((ChartsCount) line.get(i)).getSumexits();
		}
	}else if(Integer.valueOf(timespan) > 69){
		if (timespan.equals("70")) {
			//line = iChartsService.getSeventyChartsInfoByTime(startDate, endDate,startTime, endTime);
			
		}else if (timespan.equals("80")) {
			//line = iChartsService.getEightyChartsInfoByTime(startDate, endDate,startTime, endTime);
		
		}else if (timespan.equals("90")) {
			//line = iChartsService.getNinetyChartsInfoByTime(startDate, endDate,startTime, endTime);
			
		}  
		
		legend = new String[line.size()];
		series = new int[line.size()];
		series1 = new int[line.size()];
		
		for(int i=0;i<line.size();i++){
			legend[i]=((Charts) line.get(i)).getStartdate();
			series[i]=((Charts)line.get(i)).getSumenters();
			series1[i]=((Charts)line.get(i)).getSumexits();
			totalin+=series[i];
			totalout+=series1[i];
		}
		
	} else if(Integer.valueOf(timespan) < 69){
		int tspan = Integer.valueOf(timespan);
		//line=iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan);//查出的数组
		legend=new String[line.size()];//x轴，时间
		series=new int[line.size()];// y轴，数据1，入
		series1=new int[line.size()];// y轴，数据2，出
		
		for(int i = 0; i < line.size(); i++){
			legend[i] = ((ChartsCount) line.get(i)).getEnddatetime();
			series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;
			series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;
			totalin+=((ChartsCount) line.get(i)).getSumenters();
			totalout+=((ChartsCount) line.get(i)).getSumexits();
		}
	}

	Map<String, Object> charts=new HashMap<>();
	charts.put("totalin", totalin);
	charts.put("totalout", totalout);		
	charts.put("legend", legend);
	charts.put("series", series);
	charts.put("series1", series1);
	return charts;

}


@RequestMapping("/show2Double")
@ResponseBody
public Map<String,Object> toShow2Double(HttpServletRequest request,Model model){
	Logger logger = Logger.getLogger(UserController.class);
	
	String startDates =request.getParameter("startDate");//前台传入起始时间
	String endDates =request.getParameter("endDate");//前台传入结束时间
	String timespan =request.getParameter("timespan");//前台传入时间间隔
	String warnLine = request.getParameter("warnLine");// 获取警戒线参数
	String scene = request.getParameter("scene");// 获取场景参数
	
	String initDate = UserController.toString(new Date(), "yyyy-MM-dd 00:00:00");//获取当前天零点时间
	
	if(startDates==null||startDates.isEmpty()){
		 startDates=initDate;
	}
	String[] startDatearray = startDates.split(" ");//截取时间
	String startDate=startDatearray[0];//得到日期
	String startTime = startDatearray[1];//得到时间
	
	if(endDates==null||endDates.isEmpty()){
		 endDates=UserController.toString(new Date(), "yyyy-MM-dd kk:mm:00");//获取当前时间
	}
	String[] endDatearray = endDates.split(" ");//截取时间
	String endDate = endDatearray[0];//得到日期
	String endTime = endDatearray[1];//得到时间
	
	List<Object> line=null;
	String[] legendDouble=null;
	int[] series=null;
	int[] series1=null;
	int[] seriesDouble =null;
	int datespan=0;//日期差
	int totalin=0;//总进	
	int totalout=0;//总出
	int total=0;
	
	//判断时间跨度
	if(timespan == null || timespan.equals("")){
		int tspan = 60;
//		line = iChartsService.getEightyChartsInfoByTime(startDate, endDate,startTime, endTime);
		//line = iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan);
		/*legendDouble = new String[line.size()];
		series = new int[line.size()];
		series1 = new int[line.size()];
		seriesDouble = new int[line.size()];
		
		for(int i=0;i<line.size();i++){
			legendDouble[i]=((Charts) line.get(i)).getStartdate();
			series[i]=((Charts)line.get(i)).getSumenters();
			series1[i]=((Charts)line.get(i)).getSumexits();
			totalin+=series[i];
			totalout+=series1[i];
			if(scene == null || scene.equals("") || scene.equals("1")){
				seriesDouble[i]=series[i]+series1[i];//时锋
				total=totalin+totalout;
			} else{
				seriesDouble[i]=series[i]-series1[i];//滞留
				total=totalin-totalout;
			}
			
		}*/
		legendDouble=new String[line.size()];//x轴，时间
		series=new int[line.size()];// y轴，数据1，入
		series1=new int[line.size()];// y轴，数据2，出
		seriesDouble = new int[line.size()];
		
		for(int i = 0; i < line.size(); i++){
			legendDouble[i] = ((ChartsCount) line.get(i)).getEnddatetime();
			series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;
			series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;
			totalin+=((ChartsCount) line.get(i)).getSumenters();
			totalout+=((ChartsCount) line.get(i)).getSumexits();
			if(scene == null || scene.equals("") || scene.equals("1")){
				seriesDouble[i]=series[i]+series1[i];//时锋
				total=totalin+totalout;
			} else{
				seriesDouble[i]=series[i]-series1[i];//滞留
				total=totalin-totalout;
			}
		}
	}else if(Integer.valueOf(timespan) > 69){
		if (timespan.equals("70")) {
			//line = iChartsService.getSeventyChartsInfoByTime(startDate, endDate,startTime, endTime);
			
		}else if (timespan.equals("80")) {
			//line = iChartsService.getEightyChartsInfoByTime(startDate, endDate,startTime, endTime);
		
		}else if (timespan.equals("90")) {
			//line = iChartsService.getNinetyChartsInfoByTime(startDate, endDate,startTime, endTime);
			
		}  
		
		legendDouble = new String[line.size()];
		series = new int[line.size()];
		series1 = new int[line.size()];
		seriesDouble = new int[line.size()];
		
		for(int i=0;i<line.size();i++){
			legendDouble[i]=((Charts) line.get(i)).getStartdate();
			series[i]=((Charts)line.get(i)).getSumenters();
			series1[i]=((Charts)line.get(i)).getSumexits();
			totalin+=series[i];
			totalout+=series1[i];
			if(scene == null || scene.equals("") || scene.equals("1")){
				seriesDouble[i]=series[i]+series1[i];//时锋
				total=totalin+totalout;
			} else{
				seriesDouble[i]=series[i]-series1[i];//滞留
				total=totalin-totalout;
			}
		}
		
	} else if(Integer.valueOf(timespan) < 69){
		int tspan = Integer.valueOf(timespan);
		//line=iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan);//查出的数组
		legendDouble=new String[line.size()];//x轴，时间
		series=new int[line.size()];// y轴，数据1，入
		series1=new int[line.size()];// y轴，数据2，出
		seriesDouble = new int[line.size()];
		
		for(int i = 0; i < line.size(); i++){
			legendDouble[i] = ((ChartsCount) line.get(i)).getEnddatetime();
			series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;
			series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;
			totalin+=((ChartsCount) line.get(i)).getSumenters();
			totalout+=((ChartsCount) line.get(i)).getSumexits();
			if(scene == null || scene.equals("") || scene.equals("1")){
				seriesDouble[i]=series[i]+series1[i];//时锋
				total=totalin+totalout;
			} else{
				seriesDouble[i]=series[i]-series1[i];//滞留
				total=totalin-totalout;
			}
		}
	}
	
//	String startDate =request.getParameter("startDate");//前台传入起始时间
//	String endDate =request.getParameter("endDate");//前台传入结束时间
//	String timespan =request.getParameter("timespan");//前台传入时间间隔
//	String warnLine =request.getParameter("warnLine");//获取警戒线参数
//	String scene =request.getParameter("scene");//获取场景参数
//	
//	String initDate = UserController.toString(new Date(), "yyyy-MM-dd 00:00:00");//获取当前天零点时间
//	
//	if(startDate==null||startDate.isEmpty()){
//		 startDate=initDate;
//	}
//	if(endDate==null||endDate.isEmpty()){
//		 endDate=UserController.toString(new Date(), "yyyy-MM-dd kk:mm:00");//获取当前时间
//	}
//	
//	int tspan=0;
//	if(timespan==null||timespan.equals("")){
//		tspan=15;
//	}else{
//		tspan=Integer.valueOf(timespan);//获取当前时间间隔
//	}
//	
//	List<Object> line=null;
//	String[] legendDouble=null;
//	int[] series=null;
//	int[] series1=null;
//	int[] seriesDouble=null;
//	int totalin=0;
//	int totalout=0;
//	int total=0;
//	
//	try {
//		line=iChartsService.getChartsCountInfoByTime(startDate, endDate,tspan);//查出的集合
//		legendDouble=new String[line.size()];//x轴，时间
//		series=new int[line.size()];// y轴，数据1，入
//		series1=new int[line.size()];// y轴，数据2，出
//		seriesDouble=new int[line.size()]; //时锋数据or滞留数据
//		for(int i = 0; i < line.size(); i++){
//			legendDouble[i] = ((ChartsCount) line.get(i)).getEnddatetime();
//			series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;
//			series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;
//			totalin+=((ChartsCount) line.get(i)).getSumenters();
//			totalout+=((ChartsCount) line.get(i)).getSumexits();
//			if(scene==null || scene.equals("") || scene.equals("1")){
//				seriesDouble[i]=series[i]+series1[i];//时峰
//				total=totalin+totalout;
//			}else{
//				seriesDouble[i]=series[i]-series1[i];//滞留
//				total=totalin-totalout;
//			}
//			
//		}
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	
	int markLine;
	if(warnLine == null || warnLine.equals("")){
		markLine=(int) ((UserController.getMax(seriesDouble))*0.9);//默认警戒值
	}else{
		markLine = Integer.valueOf(warnLine);//前台设置的警戒值
	}
	
	Map<String, Object> charts=new HashMap<>();
	charts.put("legendDouble", legendDouble);//X轴
	charts.put("seriesDouble", seriesDouble);//Y轴
	charts.put("markLineDouble", markLine);//警戒线值
	charts.put("total", total);//总量
	return charts;
}

@RequestMapping("/showEnterExit")
@ResponseBody
public Map<String,Object> showEnterExit(HttpServletRequest request,Model model){
	Logger logger = Logger.getLogger(UserController.class);
	
	String startDates =request.getParameter("startDate");
	String endDates =request.getParameter("endDate");
	String timespan =request.getParameter("timespan");
	
	long currentTime = System.currentTimeMillis();
	currentTime -=30*60*1000;
	Date date=new Date(currentTime);
	
	String initDate = UserController.toString(date, "yyyy-MM-dd kk:mm:00");//获取当前天零点时间
	
	if(startDates==null||startDates.isEmpty()){
		 startDates=initDate;
	}
	String[] startDatearray = startDates.split(" ");//截取时间
	String startDate=startDatearray[0];
	String startTime = startDatearray[1];//得到时间
	
	if(endDates==null||endDates.isEmpty()){
		 endDates=UserController.toString(new Date(), "yyyy-MM-dd kk:mm:00");//获取当前时间
	}
	String[] endDatearray = endDates.split(" ");//截取时间
	String endDate = endDatearray[0];//得到时间
	String endTime = endDatearray[1];//得到时间
	
	List<Object> line=null;
	String[] legend=null;
	int[] series=null;
	int[] series1=null;
	int datespan=0;//日期差
	int totalin=0;
	int totalout=0;
	

	//判断时间跨度
	int tspan = 1;
	//line=iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan);//查出的数组
	legend=new String[line.size()];//x轴，时间
	series=new int[line.size()];// y轴，数据1，入
	series1=new int[line.size()];// y轴，数据2，出
	
	for(int i = 0; i < line.size(); i++){
		legend[i] = ((ChartsCount) line.get(i)).getEnddatetime();
		series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;
		series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;
		totalin+=((ChartsCount) line.get(i)).getSumenters();
		totalout+=((ChartsCount) line.get(i)).getSumexits();
	}
	

	Map<String, Object> charts=new HashMap<>();
	charts.put("totalin", totalin);
	charts.put("totalout", totalout);		
	charts.put("legend", legend);
	charts.put("series", series);
	charts.put("series1", series1);
	return charts;

}


@RequestMapping("/showSum")
@ResponseBody
public Map<String,Object> showSum(HttpServletRequest request,Model model){
	Logger logger = Logger.getLogger(UserController.class);
	
	String startDates =request.getParameter("startDate");//前台传入起始时间
	String endDates =request.getParameter("endDate");//前台传入结束时间
	String timespan =request.getParameter("timespan");//前台传入时间间隔
	String warnLine = request.getParameter("warnLine");// 获取警戒线参数
	String scene = request.getParameter("scene");// 获取场景参数
	
	long currentTime = System.currentTimeMillis();
	currentTime -=30*60*1000;
	Date date=new Date(currentTime);
	String initDate = UserController.toString(date, "yyyy-MM-dd kk:mm:00");//获取当前天零点时间
	
	if(startDates==null||startDates.isEmpty()){
		 startDates=initDate;
	}
	String[] startDatearray = startDates.split(" ");//截取时间
	String startDate=startDatearray[0];//得到日期
	String startTime = startDatearray[1];//得到时间
	
	if(endDates==null||endDates.isEmpty()){
		 endDates=UserController.toString(new Date(), "yyyy-MM-dd kk:mm:00");//获取当前时间
	}
	String[] endDatearray = endDates.split(" ");//截取时间
	String endDate = endDatearray[0];//得到日期
	String endTime = endDatearray[1];//得到时间
	
	List<Object> line=null;
	String[] legendDouble=null;
	int[] series=null;
	int[] series1=null;
	int[] seriesDouble =null;
	int datespan=0;//日期差
	int totalin=0;//总进	
	int totalout=0;//总出
	int total=0;
	
	//判断时间跨度
	int tspan = 1;
	//line = iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan);
	legendDouble=new String[line.size()];//x轴，时间
	series=new int[line.size()];// y轴，数据1，入
	series1=new int[line.size()];// y轴，数据2，出
	seriesDouble = new int[line.size()];
	
	for(int i = 0; i < line.size(); i++){
		legendDouble[i] = ((ChartsCount) line.get(i)).getEnddatetime();
		series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;
		series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;
		totalin+=((ChartsCount) line.get(i)).getSumenters();
		totalout+=((ChartsCount) line.get(i)).getSumexits();
		seriesDouble[i]=series[i]+series1[i];//时锋
		total=totalin+totalout;
	}
	
	int markLine;
	if(warnLine == null || warnLine.equals("")){
		markLine=(int) ((UserController.getMax(seriesDouble))*0.9);//默认警戒值
	}else{
		markLine = Integer.valueOf(warnLine);//前台设置的警戒值
	}
	
	Map<String, Object> charts=new HashMap<>();
	charts.put("legendDouble", legendDouble);//X轴
	charts.put("seriesDouble", seriesDouble);//Y轴
	charts.put("markLineDouble", markLine);//警戒线值
	charts.put("total", total);//总滞留量
	return charts;
}


@RequestMapping("/showDiffer")
@ResponseBody
public Map<String,Object> showDiffer(HttpServletRequest request,Model model){
	Logger logger = Logger.getLogger(UserController.class);
	
	String startDates =request.getParameter("startDate");//前台传入起始时间
	String endDates =request.getParameter("endDate");//前台传入结束时间
	String timespan =request.getParameter("timespan");//前台传入时间间隔
	String warnLine = request.getParameter("warnLine");// 获取警戒线参数
	String scene = request.getParameter("scene");// 获取场景参数
	
	long currentTime = System.currentTimeMillis();
	currentTime -=30*60*1000;
	Date date=new Date(currentTime);
	String initDate = UserController.toString(date, "yyyy-MM-dd kk:mm:00");//获取当前天零点时间
	
	if(startDates==null||startDates.isEmpty()){
		 startDates=initDate;
	}
	String[] startDatearray = startDates.split(" ");//截取时间
	String startDate=startDatearray[0];//得到日期
	String startTime = startDatearray[1];//得到时间
	
	if(endDates==null||endDates.isEmpty()){
		 endDates=UserController.toString(new Date(), "yyyy-MM-dd kk:mm:00");//获取当前时间
	}
	String[] endDatearray = endDates.split(" ");//截取时间
	String endDate = endDatearray[0];//得到日期
	String endTime = endDatearray[1];//得到时间
	
	List<Object> line=null;
	String[] legendDouble=null;
	int[] series=null;
	int[] series1=null;
	int[] seriesDouble =null;
	int datespan=0;//日期差
	int totalin=0;//总进	
	int totalout=0;//总出
	int total=0;
	
	//判断时间跨度
	int tspan = 1;
	//line = iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan);
	legendDouble=new String[line.size()];//x轴，时间
	series=new int[line.size()];// y轴，数据1，入
	series1=new int[line.size()];// y轴，数据2，出
	seriesDouble = new int[line.size()];
	
	for(int i = 0; i < line.size(); i++){
		legendDouble[i] = ((ChartsCount) line.get(i)).getEnddatetime();
		series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;
		series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;
		totalin+=((ChartsCount) line.get(i)).getSumenters();
		totalout+=((ChartsCount) line.get(i)).getSumexits();
		seriesDouble[i]=series[i]-series1[i];//滞留
		total=totalin-totalout;
	}
	
	int markLine;
	if(warnLine == null || warnLine.equals("")){
		markLine=(int) ((UserController.getMax(seriesDouble))*0.9);//默认警戒值
	}else{
		markLine = Integer.valueOf(warnLine);//前台设置的警戒值
	}
	
	Map<String, Object> charts=new HashMap<>();
	charts.put("legendDouble", legendDouble);//X轴
	charts.put("seriesDouble", seriesDouble);//Y轴
	charts.put("markLineDouble", markLine);//警戒线值
	charts.put("total", total);//总滞留量
	return charts;
}


}