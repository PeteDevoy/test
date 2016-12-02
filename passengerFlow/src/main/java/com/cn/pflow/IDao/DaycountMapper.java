package com.cn.pflow.IDao;

import com.cn.pflow.domain.Daycount;

public interface DaycountMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Daycount record);

    int insertSelective(Daycount record);

    Daycount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Daycount record);

    int updateByPrimaryKey(Daycount record);
}