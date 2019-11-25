package com.tna.campus_store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.ProductKey;
import com.tna.campus_store.beans.User;
import com.tna.campus_store.exception.BalanceException;
import com.tna.campus_store.exception.CountException;
import com.tna.campus_store.repository.UserRepository;
import com.tna.campus_store.service.UserService;

/*
 * user/{id}	GET
 * users		GET
 * user/{id}	DELETE
 * user			POST
 * user			PUT
 * 
 * user/login  	GET
 * user/register POST
 * user/purchase GET
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
	private UserService userService;
	private UserRepository userRepository;
	
	@Autowired
	public UserController(UserService userService, UserRepository userRepository) {
		super();
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@RequestMapping("/login/account")
	public Msg loginByAccount(@RequestParam("account")String account,@RequestParam("password")String password) {
		return userService.loginByAccount(account, password);
	}
	
	@RequestMapping("/login/mobile-phone")
	public Msg loginByMobilePhone(@RequestParam("verification_code")String verification_code,HttpSession session) {
		return userService.loginByMobilePhone(verification_code, session);
	}
	
	@RequestMapping("/register")
	public Msg registerByMobilePhone(HttpSession session,String account,String password) {
		return userService.registerByMobilePhone(session, account, password);
	}	
	
	@RequestMapping("/register/verify")
	public Msg registerByMobilePhoneVerify(@RequestParam("verification_code")String verification_code) {
		return userService.registerByMobilePhoneVerify(verification_code);
	}
	@RequestMapping(value = "/purchaseByAccount",method = RequestMethod.POST)
	public Msg purchaseByAccount(@RequestParam("user_id") Integer user_id, @RequestBody ProductKey pKey) {
		User user = userRepository.findOne(user_id);
		if(user!=null) {
			System.out.println(user_id);
			System.out.println(pKey.getProductId()+"--"+pKey.getCount());
			try {
				return userService.purchaseByAccount(user, pKey);
			} catch (CountException | BalanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Msg.fail(e.getMessage());
			}
		}else {
			return Msg.fail("该用户不存在!");
		}
	}
	
	@RequestMapping(value = "/purchaseMultiByAccount",method = RequestMethod.POST)
	public Msg purchaseMultiByAccount(@RequestParam("user_id") Integer user_id, @RequestBody List<ProductKey> pKeys) {
		return userService.purchaseMultiByAccount(user_id, pKeys);
	}
}
