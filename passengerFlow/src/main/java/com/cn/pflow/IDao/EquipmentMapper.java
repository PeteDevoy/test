package com.cn.pflow.IDao;

import java.util.List;
import com.cn.pflow.domain.Equipment;

public interface EquipmentMapper {
    int deleteByPrimaryKey(Long equipmentid);

    int insert(Equipment record);

    int insertSelective(Equipment record);

    Equipment selectByPrimaryKey(Long equipmentid);

    int updateByPrimaryKeySelective(Equipment record);

    int updateByPrimaryKey(Equipment record);
    
    List<Equipment> selectEquipmentsByType(String useType);
    
    long selectEquipmentIdByMac(String mac);
}