package com.cn.pflow.service;

import java.util.Map;

public interface PageService {
	
	/**
	 * 获取进出数据
	 * @param startDates
	 * @param endDates
	 * @param timespan
	 * @return
	 */
	public Map<String,Object> getIo(String startDates,String endDates,String timespan,Long area);
	
	/**
	 * 获取时锋和滞留
	 * @param startDates
	 * @param endDates
	 * @param timespan
	 * @param warnLine
	 * @param scene
	 * @return
	 */
	public Map<String,Object> getiosumdiff(String startDates, String endDates, String timespan, String warnLine, String scene,Long area);
	
	/**
	 * 地图点开进出表（只读）
	 * @param startDates
	 * @param endDates
	 * @param timespan
	 * @return
	 */
	/*public Map<String, Object> getioreadonly(String startDates, String endDates, String timespan);*/
	
	/**
	 * 地图点开时锋表（只读）
	 * @param startDates
	 * @param endDates
	 * @param timespan
	 * @return
	 */
	/*public Map<String, Object> getiosumreadonly(String startDates, String endDates, String timespan);*/
	
	/**
	 * 地图点开滞留表（只读）
	 * @param startDates
	 * @param endDates
	 * @param timespan
	 * @return
	 */
	/*public Map<String, Object> getiodiffreadonly(String startDates, String endDates, String timespan);*/
	
}
