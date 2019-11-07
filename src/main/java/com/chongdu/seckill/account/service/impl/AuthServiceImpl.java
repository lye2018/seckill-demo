package com.chongdu.seckill.account.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.chongdu.seckill.account.entity.User;
import com.chongdu.seckill.account.service.IAuthService;
import com.chongdu.seckill.common.exception.ServiceException;
import com.chongdu.seckill.common.utils.JwtUtil;
import com.chongdu.seckill.common.vo.CodeMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import io.jsonwebtoken.Claims;

@Service
public class AuthServiceImpl implements IAuthService {

	private final static Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	@Autowired
	private HttpServletRequest request;
	
//	@Autowired
//	UserMapper userMapper;
//
//	@Autowired
//	LoginLogMapper loginLogMapper;
//
//	@Autowired
//	IRedisService redisService;
	
/*	@Transactional
	public boolean authByPassword(LoginByPasswordReq req, int clientType) throws ServiceException {
		
		 try {

			 	User userInfo = null;
	            // 登录用户是否存在，否则返回错误码
			 	if (req.getUserName().matches(StaticVariables.MATCHES_USERNAME)) {
			 		
			        userInfo =  userMapper.selectOne(new QueryWrapper<User>().eq("username", req.getUserName())); 
			 	}
			 	if (req.getUserName().matches(StaticVariables.MATCHESE_PHONE)) {
			 		
			 		userInfo =  userMapper.selectOne(new QueryWrapper<User>().eq("phone", req.getUserName()));
			 	}
	                  
	            if (userInfo == null) throw new ServiceException(CodeMsg.RESOURCE_NOT_FOUNT.fillArgs("密码或用户名错误"));

	            // 解密密码
//	            String password = Encryption.decryptRSA(req.getPassword());
	            String password = req.getPassword();
	            
	            // 登录日志信息
	            LoginLog loginLog = new LoginLog();
	            loginLog.setUserId(userInfo.getId());
	            String ip = IpAddress.getIpAddr(request);
	            loginLog.setIp(ip);
	            String t = String.valueOf(System.currentTimeMillis());
	            loginLog.setLoginTime(t);
	            loginLog.setType(1);
	            loginLog.setMac(req.getMacAddress());

	            // 判断密码是否正确
	            if (!Encryption.createHash(password, userInfo.getSalt()).equals(userInfo.getHash())) {
	                throw new ServiceException(CodeMsg.UNAUTHORIZED.fillArgs("密码或用户名错误"));
	            }

	            // 保存登录日志信息
	            loginLogMapper.insertSelective(loginLog);

	            // 返回结果
	            return true;

	        } catch (ServiceException e) {
	        	logger.error("发生ServiceException异常：", e);
	            throw e;
	        } 
	        catch (Exception e) {
	        	logger.error("发生Exception异常：", e);
	            throw new ServiceException(CodeMsg.SERVER_ERROR);
	        }
	}*/

	public String createToken(User user, int clientType) throws ServiceException {
		// TODO Auto-generated method stub
		String token = JwtUtil.createJwt(0, user);
		redisService.set(user.getId(), token);
		return token;
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean authByPhone(String phone, String macAddress, int clientType) throws ServiceException {
		
		 try {

	            // 登录用户是否存在，否则返回错误码
	            User userInfo =  userMapper.selectOne(new QueryWrapper<User>().eq("phone", phone));        
	            if (userInfo == null) throw new ServiceException(CodeMsg.RESOURCE_NOT_FOUNT.fillArgs("手机号用户未注册"));

	            // 登录日志信息
	            LoginLog loginLog = new LoginLog();
	            loginLog.setUserId(userInfo.getId());
	            String ip = IpAddress.getIpAddr(request);
	            loginLog.setIp(ip);
	            String t = String.valueOf(System.currentTimeMillis());
	            loginLog.setLoginTime(t);
	            loginLog.setType(1);
	            loginLog.setMac(macAddress);

	            // 保存登录日志信息
	            loginLogMapper.insertSelective(loginLog);

	            // 返回结果
	            return true;

	        } catch (ServiceException e) {
	        	logger.error("发生ServiceException异常：", e);
	            throw e;
	        } 
	        catch (Exception e) {
	        	logger.error("发生Exception异常：", e);
	            throw new ServiceException(CodeMsg.SERVER_ERROR);
	        }
	}

	@Override
	public boolean authByToken(String token) throws ServiceException {
		
		try {
			
			// 解析token ，查询用户
	    	Claims claims = JwtUtil.parseJwt(token);
	    	String userId = (String) claims.get("id");
	    	User user = userMapper.selectById(userId);
	    	if (null == user) throw new ServiceException(CodeMsg.RESOURCE_NOT_FOUNT.fillArgs("用户不存在"));
	    
	    	// 校验token是否有效
	    	String redisToken = redisService.get(userId);
	    	if (null == redisToken || !redisToken.equals(token)) {
	    		   throw new ServiceException(CodeMsg.PROXY_AUTHENTICATION_REQUIRED.fillArgs("登录身份已失效，请重新登录"));
	    	}
	  	
	        return JwtUtil.JwtVerify(claims, user);
	        
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(CodeMsg.SERVER_ERROR);
		}

		
	}

	@Override
	public String renewToken(String token) throws ServiceException {
		
		try {
			
			// 解析token ，查询用户
	    	Claims claims = JwtUtil.parseJwt(token);
	    	String userId = (String) claims.get("id");
	    	User user = userMapper.selectById(userId);
	    	if (null == user) throw new ServiceException(CodeMsg.RESOURCE_NOT_FOUNT.fillArgs("用户不存在"));
	    	String newToken = JwtUtil.createJwt(0, user);
	    	redisService.set(user.getId(), newToken);
	        return newToken;
	        
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(CodeMsg.SERVER_ERROR);
		}
	}

	@Override
	public void removeToken(String userId) throws ServiceException {
		
		try {
			
			redisService.remove(userId);
			
		} catch (Exception e) {
			throw new ServiceException(CodeMsg.SERVER_ERROR);
		}
	}
	

}
