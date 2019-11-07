package com.chongdu.seckill.account.service;

import com.chongdu.seckill.account.entity.User;
import com.chongdu.seckill.common.exception.ServiceException;

public interface IAuthService {

//	public boolean authByPassword(LoginByPasswordReq req, int clientType) throws ServiceException;
	
//	public String createToken(User user, int clientType) throws ServiceException;
//
//	public boolean authByPhone(String phone, String macAddress, int clientType) throws ServiceException;
	
	public boolean authByToken(String token) throws ServiceException;

	public String renewToken(String token) throws ServiceException;

	/**
	 * 去除redis中的用户token信息
	 * @param userId 
	 * @throws ServiceException
	 */
	public void removeToken(String userId) throws ServiceException;
}
