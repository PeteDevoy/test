package com.cn.pflow.service;

import com.cn.pflow.domain.EquipmentCheck;

public interface IEquipmentCheckService {

	//根据设备id获取设备检测日志
	public EquipmentCheck getEquipmentCheckInfo(Long equipmentId);
	
	//添加设备检测
	public boolean addEquipmentCheckInfo(EquipmentCheck equipmentCheck);
	
	//更新设备检测
	public boolean updateEquipmentCheckInfo(EquipmentCheck equipmentCheck);
	
}
