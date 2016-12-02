package com.cn.pflow.listener;

import javax.servlet.ServletContextEvent;  

import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;

import brickstream.http.model.SmartCameraHttpServer;
import brickstream.http.model.PflowHandler;

@Component   
public class SmartCameraListener implements ServletContextListener {  
  
   // TranslateTimer tranTimer = null;  
  
    /** 
     * 创建一个初始化监听器对象，一般由容器调用 
     */  
    public SmartCameraListener() {  
        super();  
    }  
  
    /** 
     * 让Web程序运行的时候自动加载SmartCamerListenr 
     */  
    public void contextInitialized(ServletContextEvent e) {  
        System.out.println("SmartCameraListener.init");   
        try {
			new PflowHandler(true).start();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        new SmartCameraHttpServer().start();
    
    }  
  
    /** 
     * 该方法由容器调用 空实现 
     */  
    public void contextDestroyed(ServletContextEvent e) {  
    	
    }  
}  