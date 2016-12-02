package com.cn.pflow.IDao;

import com.cn.pflow.domain.EquipmentCheck;

public interface EquipmentCheckMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EquipmentCheck record);

    int insertSelective(EquipmentCheck record);

    EquipmentCheck selectByPrimaryKey(Long id);
    
    EquipmentCheck selectByEquipmentId(Long equipmentId);

    int updateByPrimaryKeySelective(EquipmentCheck record);

    int updateByPrimaryKey(EquipmentCheck record);
}