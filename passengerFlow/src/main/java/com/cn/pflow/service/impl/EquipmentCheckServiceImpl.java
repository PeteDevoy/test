package com.cn.pflow.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.pflow.IDao.EquipmentCheckMapper;
import com.cn.pflow.domain.EquipmentCheck;
import com.cn.pflow.service.IEquipmentCheckService;


@Service("equipmentCheckService")
public class EquipmentCheckServiceImpl implements IEquipmentCheckService {

	@Resource
	private EquipmentCheckMapper equipmentCheckDao;
	
	@Override
	public boolean addEquipmentCheckInfo(EquipmentCheck equipmentCheck) {
		// TODO Auto-generated method stub
		
		if(this.equipmentCheckDao.insert(equipmentCheck) > 0)
		{
			return true;
			
		}else{
			
			return false;
		}
	}

	@Override
	public boolean updateEquipmentCheckInfo(EquipmentCheck equipmentCheck) {
		// TODO Auto-generated method stub
		
		if(this.equipmentCheckDao.updateByPrimaryKey(equipmentCheck) > 0)
		{
			return true;
			
		}else{
			
			return false;
		}
	}

	@Override
	public EquipmentCheck getEquipmentCheckInfo(Long equipmentId) {
		// TODO Auto-generated method stub
		
		return this.equipmentCheckDao.selectByEquipmentId(equipmentId);
	}

}
