package com.chongdu.seckill.common.annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chongdu.seckill.account.service.AuthService;
import com.chongdu.seckill.common.exception.ServiceException;
import com.chongdu.seckill.common.vo.RestResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class AccessTokenAspect {

	private final static Logger logger = LoggerFactory.getLogger(AccessTokenAspect.class);
	
	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;
	
	@Autowired
	AuthService authService;
	
	@Around("@annotation(com.chongdu.seckill.common.annotation.AccessToken)")
	public Object doAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
		
		try {

			logger.info("url=" + request.getRequestURL());
			String token = request.getHeader("Authorization").replace("\"", "");
			logger.info("token= "+ token);
			
			
			boolean verify = authService.authByToken(token);
			if (verify) {
				Object object = pjp.proceed();
				return object;
			} else {
				
				// token 过期处理，自动续期			
				String newToken = authService.renewToken(token);
				logger.info("newToken= "+ newToken);
				response.setHeader("Authorization", newToken);
				response.setHeader("Access-Control-Expose-Headers", "Authorization");
				
				Object object = pjp.proceed();	
				return object;
			}
					
		} catch (ServiceException e) {
			return RestResponse.error(e.getCodeMsg());
		}		
	}
}
