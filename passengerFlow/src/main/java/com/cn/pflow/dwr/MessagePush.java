package com.cn.pflow.dwr;

import java.util.Collection;  
  
import org.directwebremoting.Browser;  
import org.directwebremoting.ScriptBuffer;  
import org.directwebremoting.ScriptSession;  
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.cn.pflow.dwr.DWRScriptSessionListener;  
  
public class MessagePush {  
	
	
	public static WebContext wctx = null;
	
    public void onPageLoad(final String equipmentId) {  
        // 获取当前的ScriptSession  
        if (wctx == null) {
            wctx = WebContextFactory.get();
        }
        ScriptSession scriptSession = WebContextFactory.get().getScriptSession();  
        scriptSession.setAttribute("equipmentId", equipmentId); 
        
    }  
  
    public static void send(String msg) {  
    	
        if(null==msg){  
            msg="this is a message!";  
        } 
        
        final String content = msg;  
        // 过滤器  
        ScriptSessionFilter filter = new ScriptSessionFilter() {  
            public boolean match(ScriptSession scriptSession) {  
                String equipmentId = (String) scriptSession.getAttribute("equipmentId");
                System.out.println(equipmentId +" : "+ "234".equals(equipmentId));
                return "234".equals(equipmentId);  
            }  
        };  
  
        Runnable run = new Runnable() {  
            private ScriptBuffer script = new ScriptBuffer();  
            public void run() {  
                  
                // 设置要调用的 js及参数  
                script.appendCall("show", content);  
                // 得到所有ScriptSession  
                Collection<ScriptSession> sessions = DWRScriptSessionListener.getScriptSessions();  
                // 遍历每一个ScriptSession  
                for (ScriptSession scriptSession : sessions) {
                    scriptSession.addScript(script);  
                }  
            }  
        };  
        // 执行推送  
        Browser.withAllSessionsFiltered(filter, run);  //注意这里调用了有filter功能的方法  
    }  
} 
