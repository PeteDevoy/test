package com.cn.pflow.IDao;

import com.cn.pflow.domain.Pflowconfig;

public interface PflowconfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Pflowconfig record);

    int insertSelective(Pflowconfig record);

    Pflowconfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pflowconfig record);

    int updateByPrimaryKey(Pflowconfig record);
}