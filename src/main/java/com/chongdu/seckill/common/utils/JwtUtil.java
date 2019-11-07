package com.chongdu.seckill.common.utils;

import com.chongdu.seckill.account.entity.User;
import com.chongdu.seckill.common.exception.ServiceException;
import com.chongdu.seckill.common.vo.CodeMsg;
import io.jsonwebtoken.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class JwtUtil {
	private static Properties prop;
	static {
		
//		String KEY_FILE = System.getProperty("user.dir") + "/src/main/resources/key.properties";
	 
		String KEY_FILE = "key.properties";
		 	prop = new Properties();
	    	try {
//				prop.load(new FileInputStream(KEY_FILE));
	    		prop.load(new InputStreamReader(JwtUtil.class.getClassLoader().getResourceAsStream(KEY_FILE),"UTF-8"));
		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	/**
	 * 用户登录成功后生成的JWT
	 * @param ttlMillis 过期时间 单位ms
	 * @param user 登录后的用户对象
	 * @return
	 */
	public static String createJwt(long ttlMillis, User user) {
	
		// 指定签名的时候使用的签名算法
		SignatureAlgorithm signatureAlgotithm = SignatureAlgorithm.HS256;
		
		// 生成JWT的时间
		long nowMillis  = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		
		// 创建payload私有声明
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("id", user.getId());
		claims.put("name", user.getUsername());
		
		// 生成签名时使用的secret
		String key = prop.getProperty("secret");
		
		// 生成签发人
		String subject = user.getUsername();
		
		JwtBuilder builder = Jwts.builder()
				.setClaims(claims) // 私有声明
				.setId(UUID.randomUUID().toString()) // jwt唯一标识
				.setIssuedAt(now) // 签发时间
				.setSubject(subject) // 签发主体，此jwt的所有人
				.signWith(signatureAlgotithm, key); // 设置签名实用的签名算法和使用的密钥
		
		// 默认设置7天过期
		long expMillis = ttlMillis > 0 ? nowMillis + ttlMillis : nowMillis + 604800000L;
		Date exp = new Date(expMillis); 
		// 设置过期时间
		builder.setExpiration(exp);
		
		return builder.compact();
	}
	
	/**
	 * Token解密
	 * @param token 加密后的token
	 * @return
	 * @throws ServiceException 
	 */
	public static Claims parseJwt(String token) throws ServiceException {
		
		try {
			// 签名密钥，必须和生成该toekn的签名密钥一致		
			String key = prop.getProperty("secret");
			Claims claims = Jwts.parser()
					.setAllowedClockSkewSeconds(604800) // 允许7天的偏移
					.setSigningKey(key) // 设置签名密钥
					.parseClaimsJws(token).getBody(); // 设置需要解析的JWT
			return claims;
		} catch (ExpiredJwtException e) {			
		    System.out.println(" Token expired "); 
			throw new ServiceException(CodeMsg.INVALID_TOKEN.fillArgs("登录身份已失效，请重新登录"));
		} 	
		catch (Exception e) {
			throw new ServiceException(CodeMsg.SERVER_ERROR);
		}
	
	}
	

	
	
	/**
	 * 用于检验该token是否有效
	 * @param user
	 * @return
	 */
	public static Boolean JwtVerify(Claims claims, User user) {

		// 取得当前时间
		long now = new Date().getTime();

		// 取得过期时间
		Date exp = claims.getExpiration();
		long expL = exp.getTime();
		
		System.out.println("expL:" + expL);
		if (claims.get("id").equals(user.getId()) && now <= expL) {
			return true;
		}
		return false;
	}


	public static void main(String[] args) throws ServiceException {
		parseJwt("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YW5nZXIxMjMiLCJuYW1lIjoid2FuZ2VyMTIzIiwiaWQiOiJiMzdhODIwYi1iNGMzLTRjYTEtOWMxOC1kYmU2MWNjMTVlZDkiLCJleHAiOjE1NjIxMTg5MDQsImlhdCI6MTU2MTUxNDEwNCwianRpIjoiMjIzZGQ3Y2ItZWIwMC00NmMyLWE4ZTUtYjc4MDAwOTNmMmMyIn0.NDHNQ-PLBhU5L1G71nePRa7AvDId9w0ZI2EYpznQTbg");
	}
}
