package com.cn.pflow.domain;

public class WeatherInf {

	private String tempAdvise;//温度建议
	/*private String washCarAdvise;//洗车建议
	private String coldAdvise;//感冒建议
	private String sportsAdvise;//运动建议
	private String ultravioletRaysAdvise;//紫外线建议
*/	//private OneDayWeatherInf[] weatherInfs;
	private OneDayWeatherInf weatherInfs;
	
	
	public WeatherInf(){
		tempAdvise = "";
		/*washCarAdvise = "";
		coldAdvise = "";
		sportsAdvise = "";
		ultravioletRaysAdvise = "";*/
	}
	
	public void printInf(){
		
		System.out.println(tempAdvise);
	/*	System.out.println(washCarAdvise);
		System.out.println(coldAdvise);
		System.out.println(sportsAdvise);
		System.out.println(ultravioletRaysAdvise);*/
		System.out.println(weatherInfs);

		//for(int i=0;i<weatherInfs.length;i++){
			//System.out.println(weatherInfs[i]);
		//}
		
	}
	

	public OneDayWeatherInf getWeatherInfs() {
		return weatherInfs;
	}


	public void setWeatherInfs(OneDayWeatherInf weatherInfs) {
		this.weatherInfs = weatherInfs;
	}

	public String getTempAdvise() {
		return tempAdvise;
	}

	public void setTempAdvise(String tempAdvise) {
		this.tempAdvise = tempAdvise;
	}

	

/*	public String getDressAdvise() {
		return dressAdvise;
	}


	public void setDressAdvise(String dressAdvise) {
		this.dressAdvise = dressAdvise;
	}


	public String getWashCarAdvise() {
		return washCarAdvise;
	}


	public void setWashCarAdvise(String washCarAdvise) {
		this.washCarAdvise = washCarAdvise;
	}


	public String getColdAdvise() {
		return coldAdvise;
	}


	public void setColdAdvise(String coldAdvise) {
		this.coldAdvise = coldAdvise;
	}


	public String getSportsAdvise() {
		return sportsAdvise;
	}


	public void setSportsAdvise(String sportsAdvise) {
		this.sportsAdvise = sportsAdvise;
	}


	public String getUltravioletRaysAdvise() {
		return ultravioletRaysAdvise;
	}


	public void setUltravioletRaysAdvise(String ultravioletRaysAdvise) {
		this.ultravioletRaysAdvise = ultravioletRaysAdvise;
	}
	*/
}