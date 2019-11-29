package com.tna.campus_store.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.StatusEnum;
import com.tna.campus_store.repository.UserRepository;
import com.tna.campus_store.service.ThirdPartService;
import com.tna.campus_store.utils.MessageXsendUtils;
import com.tna.campus_store.utils.RedisUtils;

@Service
public class ThirdPartServiceImpl implements ThirdPartService{
	
	private RedisUtils redisUtils;
	private UserRepository userRepository;
	
	@Autowired
	public ThirdPartServiceImpl(RedisUtils redisUtils,UserRepository userRepository) {
		super();
		this.redisUtils = redisUtils;
		this.userRepository = userRepository;
	}

	@Override
	public Msg sendVerificationCode(String phone_number,HttpSession session) {
		if(userRepository.findByPhoneNumber(phone_number)!=null) {
			String verification_code = MessageXsendUtils.getConversionCode();
			System.out.println(verification_code);
//			if(redisUtils.set(conversionCode, conversionCode, 300)&&) {
			if(redisUtils.set(verification_code, verification_code, 300)) {
//				if(MessageXsendUtils.sendMessage(phoneNumber, conversionCode)) {
					session.setAttribute("phoneNumber", phone_number);
					return Msg.success("验证码发送成功！");
//				}else {
//					return Msg.fail("验证码发送失败！");
//				}
			}else {
				return Msg.fail("服务器出错(redis...)！",StatusEnum.HINT.getCode());
			}
		}else {
			return Msg.fail("服务器出错(redis...)！",StatusEnum.HINT.getCode());
		}
	}
}
