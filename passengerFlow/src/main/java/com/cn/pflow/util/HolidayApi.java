package com.cn.pflow.util;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cn.pflow.controller.UserController;
import com.cn.pflow.domain.Holiday;
import com.cn.pflow.domain.HolidayResults;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
/**
*万年历调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/177
**/
 
public class HolidayApi {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
 
    //配置您申请的KEY
    public static final String APPKEY ="6fd7f99d1efe98acb421841e28cb69ba";
 
    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
 
    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    public static <T> T json2Bean(String jsonStr, Class<T> objClass)  
            throws JsonParseException, JsonMappingException, IOException {  
    	if(jsonStr==null || "".equals(jsonStr))
    		return null;
        ObjectMapper mapper = new ObjectMapper(); 
        //忽略无效属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonStr, objClass);  
    }
    
    //获取假日
    public static List<HolidayResults> getHolidayResults(){
    	String initDate = UserController.toString(new Date(), "yyyy-MM");//获取当前月
    	//String initDate = "2016-11";
    	StringBuilder month = new StringBuilder(initDate);
    	String str = String.valueOf(month.charAt(5));//获取第6个字符
    	if(str.equals("0")){//判断第六个字符是不是0，如果是0则替换为空，使01~09月份替换为1~9
    		month.replace(5, 6, "");
    	}
        String result =null;
        String url ="http://japi.juhe.cn/calendar/month";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("key",APPKEY);//您申请的appKey
            params.put("year-month",month);//指定月份,格式为YYYY-MM,如月份和日期小于10,则取个位,如:2012-1	
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(!object.get("error_code").equals(217701)){
            	JSONObject object2 = (JSONObject) object.get("result");
                JSONObject object3 = (JSONObject) object2.get("data");
//              net.sf.json.JSONArray jsonArray = object3.getJSONArray("holiday");
                Object object4 = object3.get("holiday");
                String objstr = object4.toString();
                if(!objstr.startsWith("[")){//如果获取的节假日只有一条，则不是json数组形式的，添加【】后转成JsonArray
                	objstr = "[" +objstr+"]";
                }
                JSONArray jsonArray = JSONArray.fromObject(objstr);
                List<HolidayResults> hldList = new ArrayList();//存放需要得到的结果HolidayResults的集合
                if(object.getInt("error_code")==0){
                    for (int i = 0; i < jsonArray.size(); i++) {
                    	String hs = jsonArray.getString(i).toString();//获取每一个节日
                    	Holiday holiday = json2Bean(hs, Holiday.class);//转换为holiday对象，含name和list
                		for(int j = 0; j < holiday.getList().size(); j++){
                    		if(holiday.getList().get(j).getStatus() == 1){//只要放假的（status=1），加班的不要
                    			HolidayResults holidayResults = new HolidayResults();
                    			holidayResults.setName(holiday.getName());//假日名称
                    			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(holiday.getList().get(j).getDate()); 
                    			String datetime = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    			holidayResults.setDate(datetime);//假日时间
                    			hldList.add(holidayResults);
                    		}
                    	}
                	}
                    return hldList;
                }else{
                    System.out.println(object.get("error_code")+":"+object.get("reason"));
                    return null;
                }
            }else{
            	return null;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}