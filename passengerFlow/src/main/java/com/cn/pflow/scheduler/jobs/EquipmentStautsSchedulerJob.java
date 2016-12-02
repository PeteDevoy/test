package com.cn.pflow.scheduler.jobs;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cn.pflow.domain.Equipment;
import com.cn.pflow.domain.EquipmentCheck;
import com.cn.pflow.listener.SpringContextListener;
import com.cn.pflow.service.IEquipmentCheckService;
import com.cn.pflow.service.IEquipmentService;

public class EquipmentStautsSchedulerJob extends QuartzJobBean {

	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
/*		System.out.println("Equipment Stauts Scheduled Job");
		
		IEquipmentService iequipmentservice = (IEquipmentService) SpringContextListener.getApplicationContext()
				.getBean(IEquipmentService.class);
		IEquipmentCheckService iequipmentcheckservice = (IEquipmentCheckService) SpringContextListener
				.getApplicationContext().getBean(IEquipmentCheckService.class);


		List<Equipment> equiList = iequipmentservice.GetEquipmentList("1");

		for (int i = 0; i < equiList.size(); i++) {

			String status = "", errorcode = "", errorinfo = "";

			EquipmentCheck equipmentCheck = new EquipmentCheck();

			equipmentCheck.setEquipmentid(equiList.get(i).getEquipmentid());
			equipmentCheck.setChecktime(new Date());

			if (GetEquipmentStatus(equiList.get(i).getIp())) {

				status = "0";

			} else {

				status = "1";
				errorcode = "";
				errorinfo = "设备连接异常!";
			}

			equipmentCheck.setErrorcode(errorcode);
			equipmentCheck.setErrorinfo(errorinfo);
			equipmentCheck.setStatus(status);

			if (iequipmentcheckservice.getEquipmentCheckInfo(equiList.get(i).getEquipmentid()) == null) {

				iequipmentcheckservice.addEquipmentCheckInfo(equipmentCheck);

			} else {

				equipmentCheck.setId(iequipmentcheckservice.getEquipmentCheckInfo(equiList.get(i).getEquipmentid()).getId());
				iequipmentcheckservice.updateEquipmentCheckInfo(equipmentCheck);
			}

		}
*/
	}

/*	public boolean GetEquipmentStatus(String ip) {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://" + ip);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();// 设置请求和传输超时时间
		httpGet.setConfig(requestConfig);
		boolean status = false;

		try {

			String statusStr = httpClient.execute(httpGet).getStatusLine().toString();

			if (statusStr.indexOf("200") > 0) {
				status = true;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// status = false;
			// e.printStackTrace();
		}

		return status;
	}*/

}
