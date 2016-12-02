package com.cn.pflow.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.cn.pflow.domain.User;


@WebService
public interface IUserService {
	
	@WebMethod
	public User getUserById(@WebParam(name = "userId") long userId);
	
	public String getUserName(long userId);
	
	public List<User> GetUsrList();
}
