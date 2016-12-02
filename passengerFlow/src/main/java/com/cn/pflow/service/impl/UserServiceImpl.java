package com.cn.pflow.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.pflow.IDao.UserMapper;
import com.cn.pflow.domain.User;
import com.cn.pflow.service.IUserService;


@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private UserMapper userDao;
	
	@Override
	public User getUserById(long userId) {
		// TODO Auto-generated method stub
		return this.userDao.selectByPrimaryKey(userId);
	}

	@Override
	public String getUserName(long userId) {
		// TODO Auto-generated method stub
		return this.userDao.selectByPrimaryKey(userId).getLoginname();
	}

	/**
	 * 查询所有用户
	 * @see com.cn.pflow.service.IUserService#GetUsrList()
	 * @param
	 * @return List<User>
	 * @author Trayvon
	 */
	@Override
	public List<User> GetUsrList()
	{
		return this.userDao.selectUsrs(null);
	}
}
