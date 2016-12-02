package com.cn.pflow.IDao;

import java.util.List;

import com.cn.pflow.domain.Pflowcount;
import com.cn.pflow.domain.PflowcountSelect;

public interface PflowcountMapper {
	
    int insert(Pflowcount record);

    int insertSelective(Pflowcount record);
    
    List<Pflowcount> selectByExample(PflowcountSelect example);
    
    List<Pflowcount> selectPfCountAfterId(long _id);
    
    List<Pflowcount> selectPfCountFewMinutesBefore(long _id, String _mac, int iLimitStart, int iLimitCnt);
    
    long selectMaxIdx();
}
