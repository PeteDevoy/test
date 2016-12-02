package com.cn.pflow.IDao;

import com.cn.pflow.domain.Area;

public interface AreaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);
}