package com.cn.pflow.IDao;

import com.cn.pflow.domain.Operator;

public interface OperatorMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(Operator record);

    int insertSelective(Operator record);

    Operator selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Operator record);

    int updateByPrimaryKey(Operator record);
    
}