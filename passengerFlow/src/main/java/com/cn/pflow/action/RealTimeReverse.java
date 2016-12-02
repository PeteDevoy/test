package com.cn.pflow.action;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.directwebremoting.Browser;
import org.directwebremoting.Container;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.ScriptSessions;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.ui.dwr.Util;

import com.cn.pflow.util.JedisUtil;

import redis.clients.jedis.Jedis;


public class RealTimeReverse implements Runnable 
{
	/* 写死的告警值 */
	static int S_PFLOW_ALARM_RETENTION = 4;	/* 滞留 */
	static int S_PFLOW_ALARM_THROUGHPUT = 3;	/* 吞吐 */
	
	
	public static WebContext wctx = null;

	boolean active = false;  
	int i = 0;  
    String timeString = "";
    String m_zEqptId = "";
	// Redis
 	static Jedis _jedisclient = null;
 	private final String JEDIS_CHNL_ENABLE_EQPT = "jedisEnableEqpt";
 	private final String JEDIS_CHNL_DISABLE_EQPT = "jedisDisableEqpt";
 	
    @SuppressWarnings("unused")
	public RealTimeReverse()
    {
    	if(false)
    	{
	        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);  
	        executor.scheduleAtFixedRate(this, 1, 1, TimeUnit.SECONDS);  
	        setEnabledAttribute(Boolean.TRUE);
    	}
    }

    public void run() 
    {
    	if(true)
        {
            setClockDisplay(new Date().toString());
        }
    }
    
    public void onPageLoad(final String eqptId) 
    {
        /* 获取当前的ScriptSession */  
        if (wctx == null) {
            wctx = WebContextFactory.get();
        }
        if (wctx != null)
        {
        	try
        	{
		        ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
		        scriptSession.setAttribute(EQPT_REALTIME_ENABLED_ATTR, eqptId);
		        m_zEqptId = eqptId;
		        
		        ScriptSessionListener listener = new ScriptSessionListener() 
		        {
					@Override
					public void sessionCreated(ScriptSessionEvent ssev) 
					{
						
					}

					@Override
					public void sessionDestroyed(ScriptSessionEvent ssev)
					{
						ScriptSession ss = ssev.getSession();
						Object attr = ss.getAttribute(EQPT_REALTIME_ENABLED_ATTR);
						if(attr == m_zEqptId)
						{
							onPageUnload();
						}
					}
		        };
		        
		        Container container = ServerContextFactory.get().getContainer();
	        	ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
		        manager.addScriptSessionListener(listener);

		        
		        if(null == _jedisclient)
					_jedisclient = JedisUtil.getJedisEx();
				if(null != eqptId)
					_jedisclient.publish(JEDIS_CHNL_ENABLE_EQPT, eqptId);
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
    }
    
    public void onPageUnload()
    {
    	if(null == _jedisclient)
    		_jedisclient = JedisUtil.getJedisEx();
    	if(null != m_zEqptId)
			_jedisclient.publish(JEDIS_CHNL_DISABLE_EQPT, m_zEqptId);
		
		_jedisclient.close();
		_jedisclient = null;
    }

    /** 
     * Called from the client to turn the clock on/off 
     */  
    public synchronized void toggle() 
    {
        i++;  
        active = !active;  
        System.out.println("#####toggle clicked!" + i);  
        System.out.println("active:" + active);  
        if (active) {  
             setClockDisplay("Started");  
        } else {  
             setClockDisplay("Stopped");  
        }
    }
    
    static int _a = 5;
    static int _b = 8;
    static int _c = 3;
    
    /*
     * @param szData 设备实时数据{数据格式: 设备ID|进|出|吞吐|滞留|是否订阅的数据}
     */
    public void RealtimeReverseData(final String szData) 
    {  
        if(null==szData || szData.equals(""))
        {
            return;
        }
        
        final String azData[] = szData.split("\\|");
		if(azData.length <=0)
		{
			return;
		}
		// 1:订阅 0:非订阅
		if(azData[5].equals("0"))
		{
			return;
		}
        
    	Browser.withAllSessionsFiltered(new RealtimeScriptSessionFilter(EQPT_REALTIME_ENABLED_ATTR, azData[0]), new Runnable()
        {
            public void run()
            {
//            	ScriptSessions.addFunctionCall("showMessages", szData);
//            	Util.setValue("clockDisplay", szData);
//				double dValue = Math.random();
//				String strValue = String.valueOf(dValue);  
//				Util.setValue("PointValue", strValue);  
            	
            	int iEnters = Integer.parseInt(azData[1]);
    			int iExits = Integer.parseInt(azData[2]);
    			int iThroughput = Integer.parseInt(azData[3]);
    			int iRetention = Integer.parseInt(azData[4]);
    			int iThroughputLeft = ((iThroughput > S_PFLOW_ALARM_THROUGHPUT) ? S_PFLOW_ALARM_THROUGHPUT : iThroughput);
    			int iThroughputRight = ((iThroughput > S_PFLOW_ALARM_THROUGHPUT) ? (iThroughput - S_PFLOW_ALARM_THROUGHPUT) : 0);
    			int iRetentionLeft = ((iRetention > S_PFLOW_ALARM_RETENTION) ? S_PFLOW_ALARM_RETENTION : iRetention);
    			int iRetentionRight = ((iRetention > S_PFLOW_ALARM_RETENTION) ? (iRetention - S_PFLOW_ALARM_RETENTION) : 0);
    			
            	if(_a > 40)
            		_a = 5;
            	if(_b > 40)
            		_b = 8;
            	if(_c > 40)
            		_c = 3;
            	
            	//ScriptSessions.addFunctionCall("set", _a+=2, _b+=1, _c+=3, _a+=2, _b+=1, _c+=3);
            	ScriptSessions.addFunctionCall("set", iThroughputLeft, iThroughputRight, iRetentionLeft, iRetentionRight, iEnters, iExits);
            }
        });
    }
    
    public void setClockDisplay(final String output) 
    {
    	Browser.withAllSessionsFiltered(new UpdatesEnabledFilter(UPDATES_ENABLED_ATTR), new Runnable()
        {
            public void run()
            {
            	Util.setValue("clockDisplay", output);  
				double dValue = Math.random();   
				String strValue = String.valueOf(dValue);  
				Util.setValue("PointValue", strValue);  
            }
        });
    }  
    
    private static String UPDATES_ENABLED_ATTR = "UPDATES_ENABLED";
    private static String EQPT_REALTIME_ENABLED_ATTR = "EQPT_REALTIME_ENABLED";
    
    /**
    *
    * @param enabled
    */
   public void setEnabledAttribute(Boolean enabled) {
	   if(WebContextFactory.get() != null)
	   {
		   ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
		   	if(scriptSession != null)
		   		scriptSession.setAttribute(UPDATES_ENABLED_ATTR, enabled);
	   }
   }
    
    private class UpdatesEnabledFilter implements ScriptSessionFilter {
    	private final String attrName;

    	public UpdatesEnabledFilter(String attrName) {
    		this.attrName = attrName;
    	}

		public boolean match(ScriptSession ss) {
			Object check = ss.getAttribute(attrName);
	        return (check != null && check.equals(Boolean.TRUE));
		}
    }
    
    private class RealtimeScriptSessionFilter implements ScriptSessionFilter 
    {
    	private final String attrName;
    	private String attrValue;

    	public RealtimeScriptSessionFilter(String attrName, String attrValue) 
    	{
    		this.attrName = attrName;
    		this.attrValue = attrValue;
    	}

		public boolean match(ScriptSession ss) 
		{
			Object check = ss.getAttribute(attrName);
	        return (check != null && check.equals(attrValue));
		}
    }
}
