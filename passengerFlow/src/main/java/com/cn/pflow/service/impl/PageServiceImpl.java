package com.cn.pflow.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.pflow.controller.UserController;
import com.cn.pflow.domain.Charts;
import com.cn.pflow.domain.ChartsCount;
import com.cn.pflow.service.PflowEquipmentAreaService;
import com.cn.pflow.service.IChartsService;
import com.cn.pflow.service.PageService;

@Service("pageService")
public class PageServiceImpl implements PageService {
	
	@Resource
	private IChartsService iChartsService;
	
	@Resource
	private PflowEquipmentAreaService pflowEquipmentAreaService;
	
	/**
	 * 获取进出数据
	 */
	@Override
	public Map<String, Object> getIo(String startDates, String endDates, String timespan, Long area) {
		
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
		int totalin=0;
		int totalout=0;
		
		//通过 区域id 获取区域下 设备id
		List<Long> equipmentids = pflowEquipmentAreaService.getEquipmentIdByAreaId(area);
		System.out.println(equipmentids);
		
		//判断时间跨度
		if(timespan == null || timespan.equals("")){
			int tspan = 1;
			line=iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan,equipmentids);//查出的
			legend=new String[line.size()];//x轴，时间
			series=new int[line.size()];// y轴，数据1，入
			series1=new int[line.size()];// y轴，数据2，出
			
			for(int i = 0; i < line.size(); i++){
				legend[i] = ((ChartsCount) line.get(i)).getEnddatetime();//x轴时间节点
				series[i] = ((ChartsCount) line.get(i)).getSumenters()/tspan;//进
				series1[i] = ((ChartsCount) line.get(i)).getSumexits()/tspan;//出
				totalin+=((ChartsCount) line.get(i)).getSumenters();//总进
				totalout+=((ChartsCount) line.get(i)).getSumexits();//总出
			}
		}else if(Integer.valueOf(timespan) > 69){
			if (timespan.equals("70")) {
				line = iChartsService.getSeventyChartsInfoByTime(startDate, endDate,startTime, endTime,equipmentids);
				
			}else if (timespan.equals("80")) {
				line = iChartsService.getEightyChartsInfoByTime(startDate, endDate,startTime, endTime,equipmentids);
			
			}else if (timespan.equals("90")) {
				line = iChartsService.getNinetyChartsInfoByTime(startDate, endDate,startTime, endTime,equipmentids);
				
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
			line=iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan,equipmentids);//查出的数组
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
		charts.put("totalin", totalin);//总进
		charts.put("totalout", totalout);//总出	
		charts.put("legend", legend);//时间轴
		charts.put("series", series);//进
		charts.put("series1", series1);//出
		return charts;
		
	}
	
	/**
	 * 获取时锋和滞留
	 */
	@Override
	public Map<String, Object> getiosumdiff(String startDates, String endDates, String timespan, String warnLine,
			String scene,Long area) {
		
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
		int totalin=0;//总进	
		int totalout=0;//总出
		int total=0;
		
		//通过 区域id 获取区域下 设备id
		List<Long> equipmentids = pflowEquipmentAreaService.getEquipmentIdByAreaId(area);
		
		//判断时间跨度
		if(timespan == null || timespan.equals("")){
			int tspan = 1;
			line = iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan,equipmentids);
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
				line = iChartsService.getSeventyChartsInfoByTime(startDate, endDate,startTime, endTime,equipmentids);
				
			}else if (timespan.equals("80")) {
				line = iChartsService.getEightyChartsInfoByTime(startDate, endDate,startTime, endTime,equipmentids);
			
			}else if (timespan.equals("90")) {
				line = iChartsService.getNinetyChartsInfoByTime(startDate, endDate,startTime, endTime,equipmentids);
				
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
			line=iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan,equipmentids);//查出的数组
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
	
	/**
	 * 地图点开进出表（只读）
	 */
	/*@Override
	public Map<String, Object> getioreadonly(String startDates, String endDates, String timespan) {
		long currentTime = System.currentTimeMillis();
		currentTime -=30*60*1000;
		Date date=new Date(currentTime);
		
		String initDate = UserController.toString(date, "yyyy-MM-dd kk:mm:00");//获取当前天零点时间
		
		if(startDates==null||startDates.isEmpty()){
			 startDates=initDate;
		}
		
		if(endDates==null||endDates.isEmpty()){
			 endDates=UserController.toString(new Date(), "yyyy-MM-dd kk:mm:00");//获取当前时间
		}
		
		List<Object> line=null;
		String[] legend=null;
		int[] series=null;
		int[] series1=null;
		int totalin=0;
		int totalout=0;
		
		List<Long> equipmentids = null;
		
		//判断时间跨度
		int tspan = 1;
		line=iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan,equipmentids);//查出的数组
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
	}*/
	
	/**
	 * 地图点开时锋表（只读）
	 */
	/*@Override
	public Map<String, Object> getiosumreadonly(String startDates, String endDates, String timespan) {
		long currentTime = System.currentTimeMillis();
		currentTime -=30*60*1000;
		Date date=new Date(currentTime);
		String initDate = UserController.toString(date, "yyyy-MM-dd kk:mm:00");//获取当前天零点时间
		
		if(startDates==null||startDates.isEmpty()){
			 startDates=initDate;
		}
		
		if(endDates==null||endDates.isEmpty()){
			 endDates=UserController.toString(new Date(), "yyyy-MM-dd kk:mm:00");//获取当前时间
		}
		
		List<Object> line=null;
		String[] legendDouble=null;
		int[] series=null;
		int[] series1=null;
		int[] seriesDouble =null;
		int totalin=0;//总进	
		int totalout=0;//总出
		int total=0;
		
		List<Long> equipmentids = null;
		
		//判断时间跨度
		int tspan = 1;
		line = iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan,equipmentids);
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
		
		 int markLine=(int) ((UserController.getMax(seriesDouble))*0.9);//默认警戒值
		
		Map<String, Object> charts=new HashMap<>();
		charts.put("legendDouble", legendDouble);//X轴
		charts.put("seriesDouble", seriesDouble);//Y轴
		charts.put("markLineDouble", markLine);//警戒线值
		charts.put("total", total);//总滞留量
		return charts;
	}*/
	
	/**
	 * 地图点开滞留表（只读）
	 */
	/*@Override
	public Map<String, Object> getiodiffreadonly(String startDates, String endDates, String timespan) {
		long currentTime = System.currentTimeMillis();
		currentTime -=30*60*1000;
		Date date=new Date(currentTime);
		String initDate = UserController.toString(date, "yyyy-MM-dd kk:mm:00");//获取当前天零点时间
		
		if(startDates==null||startDates.isEmpty()){
			 startDates=initDate;
		}
		
		if(endDates==null||endDates.isEmpty()){
			 endDates=UserController.toString(new Date(), "yyyy-MM-dd kk:mm:00");//获取当前时间
		}
		
		List<Object> line=null;
		String[] legendDouble=null;
		int[] series=null;
		int[] series1=null;
		int[] seriesDouble =null;
		int totalin=0;//总进	
		int totalout=0;//总出
		int total=0;
		
		List<Long> equipmentids = null;
		
		//判断时间跨度
		int tspan = 1;
		line = iChartsService.getChartsCountInfoByTime(startDates, endDates,tspan,equipmentids);
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
		
		 int markLine=(int) ((UserController.getMax(seriesDouble))*0.9);//默认警戒值
		
		Map<String, Object> charts=new HashMap<>();
		charts.put("legendDouble", legendDouble);//X轴
		charts.put("seriesDouble", seriesDouble);//Y轴
		charts.put("markLineDouble", markLine);//警戒线值
		charts.put("total", total);//总滞留量
		return charts;
	}*/
	
	
}
