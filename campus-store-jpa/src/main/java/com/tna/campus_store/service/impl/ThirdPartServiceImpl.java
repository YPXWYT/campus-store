package com.tna.campus_store.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.service.ThirdPartService;
import com.tna.campus_store.utils.MessageXsendUtils;
import com.tna.campus_store.utils.RedisUtils;

@Service
public class ThirdPartServiceImpl implements ThirdPartService{
	
	private RedisUtils redisUtils;
	
	@Autowired
	public ThirdPartServiceImpl(RedisUtils redisUtils) {
		super();
		this.redisUtils = redisUtils;
	}

	@Override
	public Msg sendVerificationCode(String phone_number,HttpSession session) {
		String verification_code = MessageXsendUtils.getConversionCode();
//		if(redisUtils.set(conversionCode, conversionCode, 300)&&) {
		if(redisUtils.set(verification_code, verification_code, 300)) {
//			if(MessageXsendUtils.sendMessage(phoneNumber, conversionCode)) {
				session.setAttribute("phoneNumber", phone_number);
				System.out.println(verification_code);
				return Msg.success("验证码发送成功！");
//			}else {
//				return Msg.fail("验证码发送失败！");
//			}
		}else {
			return Msg.fail("服务器出错(redis...)！");
		}
	}
}
