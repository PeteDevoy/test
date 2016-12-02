package org.zsl.testmybatis;


import javax.annotation.Resource;

import org.apache.log4j.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.cn.pflow.service.IEquipmentService;
import com.cn.pflow.service.IPfconfigService;
import com.cn.pflow.service.IPfcountService;
import com.cn.pflow.service.IUserService;


@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestMyBatis {
	
	private static Logger logger = Logger.getLogger(TestMyBatis.class);
//	private ApplicationContext ac = null;
	
	@Resource
	private IUserService userService = null;
	
	@Resource
	private IPfcountService pfcountService = null;

	@Resource
	private IPfconfigService pflowconfigService = null;

	@Resource
	private IEquipmentService equipmentService = null;

	
//	@Before
//	public void before() {
//		ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//		userService = (IUserService) ac.getBean("userService");
//	}

	@Test
	public void test1() {
		
		
		
		
/*		User user = userService.getUserById(1);
		System.out.println(user.getLoginname());
		// logger.info("值："+user.getUserName());
		logger.info(JSON.toJSONString(user));*/
		
		
/*		String configJson = pflowconfigService.getConfigInfo();
		System.out.println(JSON.parseObject(configJson).get("success") == null);
*/		
	/*	if(JSON.parseObject(configJson).get("success").toString()){	
			System.out.println("1");
		}else{
			System.out.println("2");
		}*/
	}
	
	@Test
	public void test2(){
		
/*		Pflowcount pfcount = new Pflowcount();
		pfcount.setMac("11:da:ea:4f:3b:84");
		pfcount.setStartdate("2016-10-13");
		pfcount.setStarttime("14:59:00");
		pfcount.setEnddate("2016-10-13");
		pfcount.setEndtime("15:00:00");
		pfcount.setZoneid(8);
		pfcount.setTotalenters(Long.valueOf(20));
		pfcount.setTotalexits(Long.valueOf(18));
		pfcount.setEnters(5);
		pfcount.setExits(3);
		pfcount.setStatus(0);
		
		//System.out.println(JSON.toJSON(pfcount));
		System.out.println(this.pfcountService.addPfcountInfo(pfcount));
*/		
		
/*		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");//可以方便地修改日期格式
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");//可以方便地修改日期格式

		String hehe = dateFormat.format( now );
		String hehe1 = dateFormat1.format( now );
		
		System.out.println("GetTime:" + StringUtil.String2Time("00:00:00").getTime());
		
		Date startTime = StringUtil.String2Time("00:00:00");
		
		java.util.GregorianCalendar cal = new java.util.GregorianCalendar(startTime.getYear(),
				startTime.getDay(),startTime.getHours(),startTime.getMinutes(),startTime.getSeconds());
	
		cal.add(Calendar.MINUTE, +15);
		
		System.out.println("yesterday is:"+dateFormat1.format(cal.getTime()));	
		*/
		
//		System.out.println(hehe + "---" + hehe1);
		
/*		PflowcountSelect pflowcount = new PflowcountSelect();
		
		try {
		
			 Criteria criteria = pflowcount.createCriteria();
			 criteria.andEnddateBetween(StringUtil.String2Date("2016-10-16"), StringUtil.String2Date("2016-10-17"));
			 criteria.andEndtimeBetween(StringUtil.String2Time("05:00:00"), StringUtil.String2Time("08:00:00"));
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List <Pflowcount> pc =  (List<Pflowcount>) this.pfcountService.countInfoByTime(pflowcount);
*/		
	//	System.out.println(this.pfcountService.countInfoByTime(pflowcount).size());
		//System.out.println(JSON.toJSON(this.pfcountService.countInfoByTime(pflowcount).get(0)));
	
/*		for(int i=0;i<pc.size();i++){
			System.out.println(JSON.toJSON(pc.get(i)));
		}*/

/*		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String startDateStr = "2016-10-16";
		String endDateStr = "2016-10-17";
		
		ArrayList dates = new ArrayList();
		while(StringUtil.String2Date(startDateStr).getTime() <= StringUtil.String2Date(endDateStr).getTime()){
			dates.add(startDateStr);
			GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(startDateStr.split("-")[0]),Integer.parseInt(startDateStr.split("-")[1])-1,Integer.parseInt(startDateStr.split("-")[2]));
			cal.add(Calendar.DAY_OF_MONTH, +1);
			startDateStr = dateFormat.format(cal.getTime());
		}
		
		for(int i=0;i<dates.size();i++){
			System.out.println(dates.get(i));
		}
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		String startTimeStr = "05:00:00";
		String endTimeStr = "08:00:00";
		
		ArrayList times = new ArrayList();
		while(StringUtil.String2Time(startTimeStr).getTime() <= StringUtil.String2Time(endTimeStr).getTime()){
			times.add(startTimeStr);
			GregorianCalendar cal = new GregorianCalendar(0,0,0,Integer.parseInt(startTimeStr.split(":")[0]),Integer.parseInt(startTimeStr.split(":")[1]),Integer.parseInt(startTimeStr.split(":")[2]));
			cal.add(Calendar.MINUTE, +15);
			startTimeStr = timeFormat.format(cal.getTime());
		}
		
		
		for(int i=0;i<times.size();i++){
			System.out.println(times.get(i));
		}
		
		for(int i=0;i<pc.size();i++){
			for(int j=0;j<dates.size();j++){
				for(int k=0;k<times.size();k++){
					if(pc.get(i).getStartdate().equals(dates.get(j)) && StringUtil.String2Time(times.get(k).toString()).getTime() <= StringUtil.String2Time(pc.get(i).getStarttime()).getTime() && StringUtil.String2Time(pc.get(i).getStarttime()).getTime() <= StringUtil.String2Time(times.get(k).toString()).getTime()){
					
						
					}
				}
			}
			
		}
		
*/		

/*		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String startDateStr = "2016-10-16";
		time:
		for(int i=0;i<pc.size();i++){
			if(pc.get(i).getStartdate().equals(startDateStr)){	
			}
			
			if(i == (pc.size()-1)){
				GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(startDateStr.split("-")[0]),Integer.parseInt(startDateStr.split("-")[1])-1,Integer.parseInt(startDateStr.split("-")[2]));
				cal.add(Calendar.DAY_OF_MONTH, +1);
				startDateStr = dateFormat.format(cal.getTime());
				System.out.println("---"+startDateStr);
				break time;
			}
			
		}*/
	}
	
	@Test
	public void test3(){
		
		//System.out.println(this.pfconfigService.setConfigInfo("1", "09:00:00"));
		//System.out.println(JSON.toJSONString(this.pfconfigService.getConfigInfo()));		
/*		Pflowcount pfcount = new Pflowcount();
		pfcount.setMac("11:da:ea:4f:3b:84");
		pfcount.setStartdate("2016-10-13");
		pfcount.setStarttime("14:59:00");
		pfcount.setEnddate("2016-10-13");
		pfcount.setEndtime("15:00:00");
		pfcount.setZoneid(8);
		pfcount.setTotalenters(Long.valueOf(20));
		pfcount.setTotalexits(Long.valueOf(18));
		pfcount.setEnters(5);
		pfcount.setExits(3);
		pfcount.setStatus(0);
		ResultStatus result = new ResultStatus();
		result.setData(pfcount);
		result.setStatus("0");
		result.setSuccess(true);		
		System.out.println(JSON.toJSON(result));
*/	
		
/*		String configJson = pflowconfigService.getConfigInfo();
		JSONObject resultStatus = JSON.parseObject(configJson);
		if(resultStatus.get("data") != null){
			System.out.println(JSON.parseObject(resultStatus.get("data").toString()).get("starttime"));
		}
		*/
		
	}

	@Test
	public void test4(){
		
		System.out.println(JSON.toJSONString(this.equipmentService.GetEquipmentList("1")));
		
	}
	
}
