package com.cn.pflow.service;

import java.util.List;
import javax.jws.WebService;
import com.cn.pflow.domain.Equipment;


@WebService
public interface IEquipmentService 
{
	public List<Equipment> GetEquipmentList(String useType);
	
	public Long GetEquipmentIdByMac(String mac);
	
}
