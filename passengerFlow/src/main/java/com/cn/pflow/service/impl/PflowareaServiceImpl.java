package com.cn.pflow.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.pflow.IDao.PflowareaMapper;
import com.cn.pflow.domain.Pflowarea;
import com.cn.pflow.service.PflowareaService;

@Service("pflowareaService")
public class PflowareaServiceImpl implements PflowareaService{
	@Resource
	private PflowareaMapper pflowareaDao;
	
	@Override
	public List<Pflowarea> getPflowareaList() {
		return pflowareaDao.getPflowareaList();
	}

}
