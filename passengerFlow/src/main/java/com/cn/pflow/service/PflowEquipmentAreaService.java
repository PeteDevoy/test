package com.cn.pflow.service;

import java.util.List;

public interface PflowEquipmentAreaService {
	
	/**
	 * 通过区域id查询区域内设备id
	 * @param area
	 * @return
	 */
	public List<Long> getEquipmentIdByAreaId(Long area);
	
}
