package com.cn.pflow.IDao;

import com.cn.pflow.domain.Useralarmlog;
import com.cn.pflow.domain.UseralarmlogKey;

public interface UseralarmlogMapper {
    int deleteByPrimaryKey(UseralarmlogKey key);

    int insert(Useralarmlog record);

    int insertSelective(Useralarmlog record);

    Useralarmlog selectByPrimaryKey(UseralarmlogKey key);

    int updateByPrimaryKeySelective(Useralarmlog record);

    int updateByPrimaryKey(Useralarmlog record);
}