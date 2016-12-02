package com.cn.pflow.IDao;

import com.cn.pflow.domain.Pflowalarmconfig;

public interface PflowalarmconfigMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(Pflowalarmconfig record);

    int insertSelective(Pflowalarmconfig record);

    Pflowalarmconfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pflowalarmconfig record);

    int updateByPrimaryKey(Pflowalarmconfig record);
}