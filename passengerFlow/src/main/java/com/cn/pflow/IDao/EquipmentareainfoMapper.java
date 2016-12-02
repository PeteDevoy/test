package com.cn.pflow.IDao;

import com.cn.pflow.domain.EquipmentareainfoKey;

public interface EquipmentareainfoMapper {
    int deleteByPrimaryKey(EquipmentareainfoKey key);

    int insert(EquipmentareainfoKey record);

    int insertSelective(EquipmentareainfoKey record);
}