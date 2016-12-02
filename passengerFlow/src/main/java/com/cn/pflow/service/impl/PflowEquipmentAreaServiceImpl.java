package com.cn.pflow.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.pflow.IDao.PflowEquipmentAreaMapper;
import com.cn.pflow.service.PflowEquipmentAreaService;

@Service("pflowEquipmentAreaService")
public class PflowEquipmentAreaServiceImpl implements PflowEquipmentAreaService{
	@Resource
	private PflowEquipmentAreaMapper PflowEquipmentAreaDao;
	
	/**
	 * 通过区域id查询区域内设备id
	 */
	@Override
	public List<Long> getEquipmentIdByAreaId(Long area) {
		
		return PflowEquipmentAreaDao.getEquipmentIdByAreaId(area);
	}
	
}
