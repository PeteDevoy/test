package com.cn.pflow.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * String转Date
 * @author james
 *
 */

public class StringUtil {

	/**
	 * 字符转日期类型
	 * @param date 格式 : xxxx-xx-xx
	 * @return
	 */
	public static Date String2Date(String date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Date newDate = null;
		
		try {
		   newDate = sdf.parse(date);

		} catch (ParseException e) {

		   e.printStackTrace();
		}
		return newDate;
	}
	
	/**
	 * 字符转日期类型
	 * @param time 格式: xx:xx:xx
	 * @return
	 */
	
	public static Date String2Time(String time) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

		java.util.Date newDate = null;
		
		try {
		   newDate = sdf.parse(time);

		} catch (ParseException e) {

		   e.printStackTrace();
		}
		return newDate;

	}
	
	
	
	public static String Date2String(Date date) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dateFormat.format(date);

	}
	
	
	
	public static String Time2String(Date date) {
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(date);
	}
}
