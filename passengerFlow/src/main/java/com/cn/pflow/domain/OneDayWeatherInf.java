package com.cn.pflow.domain;

//OneDayWeatherInf是自己定义的承载某一天的天气信息的实体类  
public class OneDayWeatherInf {  
  
    private String location;  //地点  
    private String date;  //日期
    private String week;  //星期
    private String tempertureOfDay;  //一天中的温度
    private String tempertureNow;   //当前温度
    private String wind;   //风度
    private String weather;   //天气
       // String picture;  //图片
    private String pmtwopointfive;  //pm2.5
  
    public OneDayWeatherInf(){  
          
        location = "";  
        date = "";  
        week = "";  
        tempertureOfDay = "";  
        tempertureNow = "";  
        wind = "";  
        weather = "";  
        //picture = "undefined";  
        pmtwopointfive = null;  
    }  
          
          
          
    public String getLocation() {  
        return location;  
    }  
    public void setLocation(String location) {  
        this.location = location;  
    }  
    public String getDate() {  
        return date;  
    }  
    public void setDate(String date) {  
        this.date = date;  
    }  
    public String getWeek() {  
        return week;  
    }  
    public void setWeek(String week) {  
        this.week = week;  
    }  
    public String getTempertureOfDay() {  
        return tempertureOfDay;  
    }  
    public void setTempertureOfDay(String tempertureOfDay) {  
        this.tempertureOfDay = tempertureOfDay;  
    }  
    public String getTempertureNow() {  
        return tempertureNow;  
    }  
    public void setTempertureNow(String tempertureNow) {  
        this.tempertureNow = tempertureNow;  
    }  
    public String getWind() {  
        return wind;  
    }  
    public void setWind(String wind) {  
        this.wind = wind;  
    }  
    public String getWeather() {  
        return weather;  
    }  
    public void setWeather(String weather) {  
        this.weather = weather;  
    }  
   /* public String getPicture() {  
        return picture;  
    }  
    public void setPicture(String picture) {  
        this.picture = picture;  
    }  */
    public String getPmtwopointfive() {
		return pmtwopointfive;
	}

	public void setPmtwopointfive(String pmtwopointfive) {
		this.pmtwopointfive = pmtwopointfive;
	}
 

}  
