package com.cn.pflow.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.pflow.IDao.EquipmentMapper;
import com.cn.pflow.domain.Equipment;
import com.cn.pflow.service.IEquipmentService;

@Service("equipmentService")
public class EquipmentServiceImpl implements IEquipmentService 
{
	@Resource
	private EquipmentMapper equipmentDao;
	
	
	/**
	 * 查询人群所有设备
	 * @param useType 0:人脸设备 1:人群设备
	 * @return List<Equipment>
	 * @author Trayvon
	 */
	
	@Override
	public List<Equipment> GetEquipmentList(String useType)
	{
		return this.equipmentDao.selectEquipmentsByType(useType);
	}

	
	/**
	 * 根据mac查询设备id
	 * @author james
	 * @param mac
	 * @return id
	 */
	
	@Override
	public Long GetEquipmentIdByMac(String mac) {
		// TODO Auto-generated method stub
		return this.equipmentDao.selectEquipmentIdByMac(mac);
	}
	
}
