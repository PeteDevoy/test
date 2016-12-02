package com.cn.pflow.util;

//需要导入解析JSON格式数据的第三方包   请百度搜索并下载，导入“jsonlib.rar”包
import java.util.Date;

import com.cn.pflow.domain.OneDayWeatherInf;
import com.cn.pflow.domain.WeatherInf;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


public class resolveWeatherInf {
	// 解析JSON数据 要解析的JSON数据:strPar
	// WeatherInf 是自己定义的承载所有的天气信息的实体类，包含了四天的天气信息。详细定义见后面。
	public static WeatherInf resolveWeatherInf(String strPar){
	
	 JSONObject dataOfJson = JSONObject.fromObject(strPar);
        
	 if(dataOfJson.getInt("error")!=0){
		 return null;
	 }
	 
	 //保存全部的天气信息。
	 WeatherInf weatherInf = new WeatherInf();

	 //从json数据中取得的时间�?
        String date = dataOfJson.getString("date");
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        Date today = new Date(year-1900,month-1,day);

        JSONArray results=dataOfJson.getJSONArray("results");//设置JSON的数组节点
        JSONObject results0=results.getJSONObject(0);
        
        String location = results0.getString("currentCity");//获取地点
        String pmTwoPointFive = null;

        if(results0.getString("pm25").isEmpty()){
        	pmTwoPointFive = "0";
        }else if(results0.getInt("pm25")>0 && results0.getInt("pm25")<=50){
        	pmTwoPointFive = "优";
        	//pmTwoPointFive = results0.getInt("pm25");//获取pm2.5
        }else if(results0.getInt("pm25")>50 && results0.getInt("pm25")<=100){
        	pmTwoPointFive = "良";
        }else if(results0.getInt("pm25")>100 && results0.getInt("pm25")<=150){
        	pmTwoPointFive = "轻度污染";
        }else if(results0.getInt("pm25")>150 && results0.getInt("pm25")<=200){
        	pmTwoPointFive = "中度污染";
        }else if(results0.getInt("pm25")>200 && results0.getInt("pm25")<=300){
        	pmTwoPointFive = "重度污染";
        }else if(results0.getInt("pm25")>300 ){
        	pmTwoPointFive = "严重污染";
        }
        //System.out.println(results0.get("pm25").toString()+"11");
        
        try{
        	
	        JSONArray index = results0.getJSONArray("index");
	        
	        JSONObject index0 = index.getJSONObject(0);//穿衣
/*	        JSONObject index1 = index.getJSONObject(1);//洗车
	        JSONObject index2 = index.getJSONObject(2);//感冒
	        JSONObject index3 = index.getJSONObject(3);//运动
	        JSONObject index4 = index.getJSONObject(4);//紫外线强度
*/	        

	    	String TempAdvise = index0.getString("zs");//穿衣建议
/*	    	String washCarAdvise = index1.getString("des");//洗车建议
	    	String coldAdvise = index2.getString("des");//感冒建议
	    	String sportsAdvise = index3.getString("des");//运动建议
	    	String ultravioletRaysAdvise = index4.getString("des");//紫外线建议
*/	        
	    	weatherInf.setTempAdvise(TempAdvise);
/*	    	weatherInf.setWashCarAdvise(washCarAdvise);
	    	weatherInf.setColdAdvise(coldAdvise);
	    	weatherInf.setSportsAdvise(sportsAdvise);
	    	weatherInf.setUltravioletRaysAdvise(ultravioletRaysAdvise);*/
        	
        }catch(JSONException jsonExp){
        	
	    	weatherInf.setTempAdvise("无温度");
	    	/*weatherInf.setWashCarAdvise("你洗还是不洗，灰尘都在哪里，不增不减。");
	    	weatherInf.setColdAdvise("一天一个苹果，感冒不来找我！多吃水果和蔬菜。");
	    	weatherInf.setSportsAdvise("生命在于运动！不要总宅在家里哦！");
	    	weatherInf.setUltravioletRaysAdvise("心灵可以永远年轻，皮肤也一样可以！");*/
        }
            
        JSONArray weather_data = results0.getJSONArray("weather_data");//weather_data中有四项�?
        
                    //OneDayWeatherInf是自己定义的承载某一天的天气信息的实体类，详细定义见后面。
		//OneDayWeatherInf oneDayWeatherInfS = new OneDayWeatherInf;
		//OneDayWeatherInf[] oneDayWeatherInfS = new OneDayWeatherInf[1];
		
		//for(int i=0;i<4;i++){
		OneDayWeatherInf oneDayWeatherInfS = new OneDayWeatherInf();//新建4个实体类对象，用来存储
		//oneDayWeatherInfS[0] = new OneDayWeatherInf();//新建4个实体类对象，用来存储
		//}
		
        //for(int i=0;i<weather_data.size();i++){
        	
        	JSONObject OneDayWeatherinfo=weather_data.getJSONObject(0);
        	String dayData = OneDayWeatherinfo.getString("date");
        	OneDayWeatherInf oneDayWeatherInf = new OneDayWeatherInf();

        	oneDayWeatherInf.setDate((today.getYear()+1900)+"-"+(today.getMonth()+1)+"-"+today.getDate());
        	today.setDate(today.getDate()+1);//增加一天
        	
        	oneDayWeatherInf.setLocation(location);
        	oneDayWeatherInf.setPmtwopointfive(pmTwoPointFive);
        	
        	//if(i==0){//第一个，也就是当天的天气，在date字段中最后包含了实时天气
        		int beginIndex = dayData.indexOf("：");//date里的：第一次出现的位置
        		int endIndex = dayData.indexOf(")");//date里的）第一次出现的位置
        		if(beginIndex>-1){
        			oneDayWeatherInf.setTempertureNow(dayData.substring(beginIndex+1, endIndex));
        			oneDayWeatherInf.setWeek(OneDayWeatherinfo.getString("date").substring(0,2));
        		}else{
        			oneDayWeatherInf.setTempertureNow(" ");
        			oneDayWeatherInf.setWeek(OneDayWeatherinfo.getString("date").substring(0,2));
        		}

        	//}else{
        		//oneDayWeatherInf.setWeek(OneDayWeatherinfo.getString("date"));
        	//}
        	
        	oneDayWeatherInf.setTempertureOfDay(OneDayWeatherinfo.getString("temperature"));
        	oneDayWeatherInf.setWeather(OneDayWeatherinfo.getString("weather"));
        	//oneDayWeatherInf.setWeather(weather);
        	//oneDayWeatherInf.setWind(OneDayWeatherinfo.getString("wind"));
        	        	
	        oneDayWeatherInfS = oneDayWeatherInf;
       // }
        
        weatherInf.setWeatherInfs(oneDayWeatherInfS);

	return weatherInf;
}
}
