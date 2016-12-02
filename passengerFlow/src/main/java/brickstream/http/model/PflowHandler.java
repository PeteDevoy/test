package brickstream.http.model;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.cn.pflow.action.JedisSubThreadEx;
import com.cn.pflow.domain.*;
import com.cn.pflow.listener.SpringContextListener;
import com.cn.pflow.service.IPfcountService;
import com.cn.pflow.service.IUserService;
import com.cn.pflow.service.IEquipmentService;
import com.cn.pflow.service.IAlarmConfigService;
import com.cn.pflow.util.JedisUtil;

/*
大分类		小分类		表示值			备注
04		0		黑名单报警		报警类型
04		1		陌生人报警	
04		2		人流异常报警	
*/


public class PflowHandler extends Thread
{
	/* static value */
	static long _maxIdx = 0;
	static int _alarmIdx = 1;
	static String _DateTmNow = null;
	static String _DateTmOld = null;
	static String _DateTmRefresh = null;
	static String _DateTmAlarmRetention = null;
	static SimpleDateFormat timeFormat = null;
	
	/* document factory */
	static DocumentBuilderFactory ms_tDocFactory = null;
	static DocumentBuilder ms_tDocBuilder = null;
	
	/* 订阅实时数据的设备列表<MAC, ID> */
	static Map<String, String> subList = new HashMap<String, String>();
	
	
	/* 上1秒钟进出数 */
	static int ms_iPreSTotalEnters = 0;
	static int ms_iPreSTotalExits = 0;
	/* 历史滞留人数 */
	static int ms_iHisRetention = 0;
	/* 历史进出数 */
	static int ms_iHisEnters = -1;
	static int ms_iHisExits = -1;
	
	/* 写死的告警值 */
	static int S_PFLOW_ALARM_RETENTION = 4;	/* 滞留 */
	static int S_PFLOW_ALARM_RETENTION_TIME_SS = 300;	/* 滞留时间/300秒 */
	static int S_PFLOW_ALARM_THROUGHPUT = 3;	/* 吞吐 */
	static int S_PFLOW_ALARM_TREND = 2;	/* 趋势 */
	static int S_PFLOW_ALARM_TREND_PERCENT = 60;	/* 趋势百分比 */
	
	
	/* define */
	static int PFLOW_ALARM_TYPE_HIGHTHROUGH = 2;
	static int PFLOW_MSG_TYPE_ALARM = 0;
	static int PFLOW_MSG_TYPE_OTHER = 1;
	
	
	/* service */
	private static IAlarmConfigService pfAlarmCfgService = null;
	private static IUserService usrService = null;
	private static IEquipmentService equipmentService = null;
	private static IPfcountService pfCountService = null;
	
	/* users */
	private static List<User> _usrs;
	
	/* equipments */
	private static List<Equipment> _equipments;
	
	/* alarm */
	private static Pflowalarmconfig _alarmcfg;
	
	/* new data */
	private List<Pflowcount> _data;
	
	/* last data */
	private List<Pflowcount> _lastData;
	
	
	
	/* Constructor */
	public PflowHandler(boolean bCreateSubThread) throws Exception
	{
		/* cache */
		RefreshCacheData(false);
		
		if(null == timeFormat)
		{
			timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		}
		_DateTmNow = timeFormat.format(new Date());
		_DateTmOld = timeFormat.format(new Date());
		_DateTmRefresh = timeFormat.format(new Date());
		
		if(bCreateSubThread)
			new JedisSubThreadEx(JedisUtil.getJedisPoolEx()).start();
	}
	
	/* Constructor */
	public PflowHandler() { }
	
	/*
	 * 添加订阅实时数据的设备
	 */
	public int AddSubscribeEqpt(String eqptId)
	{
		if(subList.containsValue(eqptId))
			return 1;
		
		String zMac = GetEqptMacRecurrenceSearch(_equipments, 0, eqptId);
		
		if(null == zMac)
			return 0;
		
		subList.put(zMac, eqptId);
		
		return 1;
	}
	
	/*
	 * 删除订阅实时数据的设备
	 */
	public int DelSubscribeEqpt(String eqptId)
	{
		if(subList.containsValue(eqptId))
		{
			for(Map.Entry<String, String> entry : subList.entrySet())
			{
			    if(eqptId.equals(entry.getValue()))
			    {
		    		subList.remove(entry.getKey());
		    		break;
			    }
			}
		}
		return 1;
	}
	
	/*
	 * 检索设备
	 */
	private static long GetEqptIdRecurrenceSearch(List<Equipment> _listObj, int idx, String _mac)
	{
	    if (null == _listObj)
	    {
	        return -1;
	    }
	    
	    if(_listObj.size() <= idx)
	    {
	    	return -1;
	    }

	    if (_listObj.get(idx).getMacaddress().equals(_mac))
	    {
	        return _listObj.get(idx).getEquipmentid();
	    }

	    return GetEqptIdRecurrenceSearch(_listObj, ++idx, _mac);
	}
	
	/*
	 * 检索设备
	 */
	private static String GetEqptMacRecurrenceSearch(List<Equipment> _listObj, int idx, String _id)
	{
	    if (null == _listObj)
	    {
	        return null;
	    }
	    
	    if(_listObj.size() <= idx)
	    {
	    	return null;
	    }

	    if (_listObj.get(idx).getEquipmentid() == Long.parseLong(_id))
	    {
	        return _listObj.get(idx).getMacaddress();
	    }

	    return GetEqptMacRecurrenceSearch(_listObj, ++idx, _id);
	}
	
	
	/*
	 * @return bigger
	 */
	private static long ex_max(long _a, long _b)
	{
		return ((_a) > (_b) ? (_a) : (_b));
	}
	
	
	/*
	 * @return generate alarm id
	 */
	private static String generate_alarm_id(int alarmType, long devId)
	{
		if(null == timeFormat)
		{
			timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		}
		_DateTmNow = timeFormat.format(new Date());
		if(_DateTmNow.equals(_DateTmOld))
		{
			++_alarmIdx;
		}
		else
		{
			_alarmIdx = 1;
		}
		
		_DateTmOld = _DateTmNow;
		
		return String.format("%s%03d%09d%d", _DateTmNow, _alarmIdx, devId, alarmType%10);
	}
	
	
	/*
	 * Refresh cache every 10 minutes
	 * @param bWait 是否等待
	 */
	private static void RefreshCacheData(boolean bWait)
	{
		try 
		{
			boolean bRefresh = false;
			if(null == timeFormat)
			{
				timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			}
			_DateTmNow = timeFormat.format(new Date());
			
			if(false == bWait)
			{
				bRefresh = true;
			}
			else if((timeFormat.parse(_DateTmNow).getTime() - timeFormat.parse(_DateTmRefresh).getTime())/1000 > 600)
			{
				bRefresh = true;
			}
			else
			{
				// ...
			}
			
			if(bRefresh)
			{
				/* 数据库服务 */
				if(null == usrService)
					usrService = (IUserService) SpringContextListener.getApplicationContext().getBean(IUserService.class);
				/* 数据库服务 */
				if(null == equipmentService)
					equipmentService = (IEquipmentService) SpringContextListener.getApplicationContext().getBean(IEquipmentService.class);
				/* 数据库服务 */
				if(null == pfCountService)
					pfCountService = (IPfcountService) SpringContextListener.getApplicationContext().getBean(IPfcountService.class);
				/* 数据库服务 */
				if(null == pfCountService)
					pfAlarmCfgService = (IAlarmConfigService) SpringContextListener.getApplicationContext().getBean(IAlarmConfigService.class);
				
				
				if(null != usrService)
				{
					/* 查询用户 */
					_usrs = usrService.GetUsrList();
				}
				if(null != equipmentService)
				{
					/* 查询人群设备 */
					_equipments = equipmentService.GetEquipmentList("1");
				}
				if(null != pfAlarmCfgService)
				{
					/* 告警配置 */
					_alarmcfg = pfAlarmCfgService.getAlarmConfig();
					
					// more alarm settings ...
				}
				
				_DateTmRefresh = _DateTmNow;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * handler method
	 */
	private void startPflowHandlerServer() 
	{
		long _equipmentid = 0;
		int iNum4Alarm = 0;
		int iExPeak = 0;
		
		Useralarmlog _alarmUal = null;
		Alarminfo _alarmAi = null;
		
		/* 获取索引号 */
		_maxIdx = pfCountService.GetMaxIdxFromPfCount();
		
		do
		{
			/* server infinite loop */
			try 
			{
				/* cache */
				RefreshCacheData(true);
				
				/* 查询未检索记录 */
				_data = pfCountService.GetPfCountAfterId(_maxIdx);
				
				if(null == _data || _data.size() <= 0 || null == _alarmcfg)
				{
					Thread.sleep(1000);
					continue;
				}
				
				
				for(Pflowcount _param : _data)
				{
					iNum4Alarm = 0;
					iExPeak = 0;
					
					if(_alarmcfg.getTimeinterval() > 1)
					{
						_lastData = pfCountService.GetPfCountFewMinutesBefore(_param.getId(), _param.getMac(), _alarmcfg.getTimeinterval());
						for(int _iCnt = 0; null != _lastData && _iCnt < _lastData.size(); _iCnt++)
						{
							iNum4Alarm += _lastData.get(_iCnt).getEnters();
							iNum4Alarm += _lastData.get(_iCnt).getExits();
						}
						iExPeak = _alarmcfg.getPeak();
					}
					else
					{
						iNum4Alarm = _param.getEnters() + _param.getExits();
						iExPeak = _alarmcfg.getBasicpeak();
					}
					
					/* 告警类型:高通过量告警  */
					if(iNum4Alarm >= iExPeak)
					{
						/* 告警id */
						String alarmId = null;
						String _alarmnote = String.format("人流数量为%d人, 超过告警值%d人(告警阀值%d).", iNum4Alarm, iNum4Alarm - iExPeak, iExPeak);
						
						_equipmentid = GetEqptIdRecurrenceSearch(_equipments, 0, _param.getMac());
						if(_equipmentid < 0)
						{
							continue;
						}
						alarmId = generate_alarm_id(PFLOW_ALARM_TYPE_HIGHTHROUGH, _equipmentid);
						
						_alarmUal = new Useralarmlog();
						_alarmAi = new Alarminfo();
						
						_alarmAi.setAlarmid(alarmId);
						_alarmAi.setAlarmtype(String.valueOf(PFLOW_ALARM_TYPE_HIGHTHROUGH));
						_alarmAi.setEquipmentid(_equipmentid);
						_alarmAi.setNote(_alarmnote);
						
						_alarmUal.setAlarmid(alarmId);
						_alarmUal.setEquipmentid(_equipmentid);
						_alarmUal.setMsgtype(String.valueOf(PFLOW_MSG_TYPE_ALARM));
						_alarmUal.setNote(_alarmnote);
						
						/* 告警 */
						pfCountService.AddHighthroughAlarm2ai(_alarmAi);
						for(int _iCnt = 0; null != _usrs && _iCnt < _usrs.size(); _iCnt++)
						{
							_alarmUal.setUserid(_usrs.get(_iCnt).getId());
							pfCountService.AddHighthroughAlarm2ual(_alarmUal);
						}
					}
					// more alarms ...
					
					_maxIdx = ex_max(_maxIdx, _param.getId());
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			} 
			finally 
			{

			}
			
		}while(true);
	}

	
	@Override
	public void run() 
	{
		try 
		{
			/* Invoke the handler method */
			startPflowHandlerServer();
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
	
	
	/*
	 * 
	 */
	private static void SendAlarm2Db(String szEqptId, String szNote)
	{
		long _equipmentid = 0;
		String alarmId = null;
		Useralarmlog _alarmUal = null;
		Alarminfo _alarmAi = null;
		
		if(null == _equipments || null == _usrs)
		{
			/* cache */
			RefreshCacheData(false);
		}
//		_equipmentid = GetEqptIdRecurrenceSearch(_equipments, 0, szMac);
//		if(_equipmentid < 0)
//		{
//			return;
//		}
		_equipmentid = Long.parseLong(szEqptId);
		alarmId = generate_alarm_id(PFLOW_ALARM_TYPE_HIGHTHROUGH, _equipmentid);
		
		_alarmUal = new Useralarmlog();
		_alarmAi = new Alarminfo();
		
		_alarmAi.setAlarmid(alarmId);
		_alarmAi.setAlarmtype(String.valueOf(PFLOW_ALARM_TYPE_HIGHTHROUGH));
		_alarmAi.setEquipmentid(_equipmentid);
		_alarmAi.setNote(szNote);
		
		_alarmUal.setAlarmid(alarmId);
		_alarmUal.setEquipmentid(_equipmentid);
		_alarmUal.setMsgtype(String.valueOf(PFLOW_MSG_TYPE_ALARM));
		_alarmUal.setNote(szNote);
		
		/* 告警 */
		pfCountService.AddHighthroughAlarm2ai(_alarmAi);
		for(int _iCnt = 0; null != _usrs && _iCnt < _usrs.size(); _iCnt++)
		{
			_alarmUal.setUserid(_usrs.get(_iCnt).getId());
			pfCountService.AddHighthroughAlarm2ual(_alarmUal);
		}
	}
	
	
	/*
	 * 检测是否是设备实时数据
	 * @param szXml 设备xml报文
	 * @return 不是实时数据返回null
	 *	数据格式: 设备ID|进|出|吞吐|滞留|是否订阅的数据
	 */
	public static String parseXml2(String szXml) throws Exception
	{
		//String szXml = "<RealTimeMetrics  SiteId=\"China Telecom 2\"><Properties><MacAddress>74:da:ea:4f:3b:84</MacAddress><IpAddress>192.168.1.8</IpAddress><HostName>Cam-16080554</HostName><HttpPort>80</HttpPort><HttpsPort>443</HttpsPort><Timezone>8</Timezone><DST>0</DST><HwPlatform>3210</HwPlatform><SerialNumber>16080554</SerialNumber><DeviceType>0</DeviceType></Properties><RTReport  Date=\"2016-11-02T16:07:39\"><RTObject  Id=\"0\" DeviceId=\"China Telecom 2\" Devicename=\"China Telecom 2\" ObjectType=\"0\" Name=\"Main2\"><RTCount  TotalEnters=\"13\" TotalExits=\"6\"/></RTObject></RTReport></RealTimeMetrics>";

		do {
			String zMac = null;
			int iTotalEnters = 0;
			int iTotalExits = 0;
			int iCurSecExits = 0;
			int iCurSecEnters = 0;
			int iThroughput = 0;
			int iRetention = 0;
			long _equipmentid = 0;
			
			if(null == szXml || szXml.equals(""))
			{
				break;
			}
			
			if(null == ms_tDocFactory)
			{
				ms_tDocFactory = DocumentBuilderFactory.newInstance();
			}
			if(null == ms_tDocBuilder)
			{
				ms_tDocBuilder = ms_tDocFactory.newDocumentBuilder();
			}
			
			InputStream is = new ByteArrayInputStream(szXml.getBytes());
			Document document = ms_tDocBuilder.parse(is);
			
			if(null == document.getElementsByTagName("RealTimeMetrics").item(0))
			{
				break;
			}
			
			// System.out.println(document.getElementsByTagName("RTReport").item(0).getAttributes().getNamedItem("Date").getNodeValue());
			zMac = document.getElementsByTagName("MacAddress").item(0).getFirstChild().getNodeValue();
			iTotalEnters = Integer.parseInt(document.getElementsByTagName("RTCount").item(0).getAttributes().getNamedItem("TotalEnters").getNodeValue());
			iTotalExits = Integer.parseInt(document.getElementsByTagName("RTCount").item(0).getAttributes().getNamedItem("TotalExits").getNodeValue());
			
			/* 程序重启 */
			if(ms_iHisEnters < 0 || ms_iHisExits < 0)
			{
				ms_iHisEnters = iTotalEnters;
				ms_iHisExits = iTotalExits;
				ms_iHisRetention = ms_iHisEnters - ms_iHisExits;
				
				ms_iPreSTotalEnters = iTotalEnters;
				ms_iPreSTotalExits = iTotalExits;
			}
			/* 设备断电重启 */
			if(ms_iHisEnters > iTotalEnters || ms_iHisExits > iTotalExits)
			{
				ms_iHisEnters = iTotalEnters;
				ms_iHisExits = iTotalExits;
				ms_iHisRetention = ms_iHisEnters - ms_iHisExits;
				
				ms_iPreSTotalEnters = iTotalEnters;
				ms_iPreSTotalExits = iTotalExits;
			}
			
			iCurSecEnters = iTotalEnters - ms_iPreSTotalEnters;
			iCurSecExits = iTotalExits - ms_iPreSTotalExits;
			iThroughput = iCurSecEnters + iCurSecExits;
			//iRetention = iTotalEnters - iTotalExits - ms_iHisRetention;
			iRetention = iTotalEnters - iTotalExits;
			
			ms_iPreSTotalEnters = iTotalEnters;
			ms_iPreSTotalExits = iTotalExits;
			
			_equipmentid = GetEqptIdRecurrenceSearch(_equipments, 0, zMac);
			if(_equipmentid < 0)
			{
				break;
			}
			
			return String.format("%d|%d|%d|%d|%d|%d", _equipmentid, iCurSecEnters, iCurSecExits, iThroughput, iRetention, (subList.containsKey(zMac) ? 1 : 0));
			
		} while(false);
		
		return null;
	}
	
	
	/*
	 * 告警处理
	 * @param zData 设备实时数据{数据格式: 设备ID|进|出|吞吐|滞留|是否订阅的数据}
	 */
	public static void parseXml2Alarm(String zData) throws Exception
	{
		if(null == zData || zData.equals(""))
		{
			return;
		}
		
		String azData[] = zData.split("\\|");
		if(azData.length <=0)
		{
			return;
		}
		
		if(null == timeFormat)
		{
			timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		}
		if(null == _alarmcfg)
		{
			/* cache */
			RefreshCacheData(false);
		}
		
		/* 告警类型:超高吞吐告警 */
		if(Integer.parseInt(azData[3]) >= S_PFLOW_ALARM_THROUGHPUT)
		{
			int iThroughput = Integer.parseInt(azData[3]);
			String _alarmnote = String.format("超高吞吐量告警:人流数量为%d人, 超过告警值%d人(告警阀值%d).", iThroughput, iThroughput - S_PFLOW_ALARM_THROUGHPUT, S_PFLOW_ALARM_THROUGHPUT);
			SendAlarm2Db(azData[0], _alarmnote);
		}
		
		/* 告警类型:滞留告警 */
		if(Integer.parseInt(azData[4]) >= S_PFLOW_ALARM_RETENTION)
		{
			String _TmNow = timeFormat.format(new Date());
			
			if(null == _DateTmAlarmRetention)
			{
				_DateTmAlarmRetention = timeFormat.format(new Date());
			}
			
			if((timeFormat.parse(_TmNow).getTime() - timeFormat.parse(_DateTmAlarmRetention).getTime())/1000 >= S_PFLOW_ALARM_RETENTION_TIME_SS)
			{
				int iRetention = Integer.parseInt(azData[4]);
				String _alarmnote = String.format("滞留告警:滞留人数为%d人, 超过告警值%d人(告警阀值%d).", iRetention, iRetention - S_PFLOW_ALARM_RETENTION, S_PFLOW_ALARM_RETENTION);
				SendAlarm2Db(azData[0], _alarmnote);
				_DateTmAlarmRetention = null;
			}
		}
		else
		{
			if(null != _DateTmAlarmRetention)
			{
				_DateTmAlarmRetention = null;
			}
		}
		
		/* 告警类型:趋势告警 */
		if(Integer.parseInt(azData[1]) + Integer.parseInt(azData[2]) >= S_PFLOW_ALARM_TREND)
		{
			int iTotal = Integer.parseInt(azData[1]) + Integer.parseInt(azData[2]);
			if(0 == Integer.parseInt(azData[1]) /*|| 0 == Integer.parseInt(azData[2])*/ 
					/*|| Integer.parseInt(azData[1]) * 100 / iTotal >= S_PFLOW_ALARM_TREND_PERCENT*/
					|| Integer.parseInt(azData[2]) * 100 / iTotal >= S_PFLOW_ALARM_TREND_PERCENT)
			{
				String _alarmnote = String.format("人群流向异常告警:人群异常流动.");
				SendAlarm2Db(azData[0], _alarmnote);
			}
		}
	}
}


