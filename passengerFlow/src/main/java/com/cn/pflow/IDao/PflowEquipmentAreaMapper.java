package com.cn.pflow.IDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PflowEquipmentAreaMapper {
	
	/**
	 * 通过区域id查询区域内设备id
	 * @param area
	 * @return
	 */
	public List<Long> getEquipmentIdByAreaId(@Param("area")Long area);
	
}
