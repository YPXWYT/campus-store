package com.tna.campus_store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.ProductKey;
import com.tna.campus_store.beans.Role;
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

	@RequestMapping(value="/login/account",method = RequestMethod.POST)
	public Msg loginByAccount(@RequestParam("account")String account,@RequestParam("password")String password) {
		return userService.loginByAccount(account, password);
	}
	
	@RequestMapping("/login/mobile_phone")
	public Msg loginByMobilePhone(@RequestParam("verification_code")String verification_code,HttpSession session,String phone_number) {
		return userService.loginByMobilePhone(verification_code, session,phone_number);
	}
	
	@RequestMapping(value="/register",method = RequestMethod.POST)
	public Msg registerByMobilePhone(HttpSession session,@RequestBody User user,
			@RequestParam(value = "role_id",defaultValue = "2",required = false) Integer role_id) {
		return userService.registerByMobilePhone(session, user, role_id);
	}	
	
	@RequestMapping(value="/register/verify",method = RequestMethod.GET)
	public Msg registerByMobilePhoneVerify(@RequestParam("verification_code")String verification_code,
			@RequestParam("verification_code")String phone_number) {
		return userService.registerByMobilePhoneVerify(verification_code);
	}
	@RequestMapping(value = "/purchase_a",method = RequestMethod.POST)
	public Msg purchaseByAccount(@RequestParam("user_id") Integer user_id, @RequestBody ProductKey pKey) {
		User user = userRepository.findOne(user_id);
		if(user!=null) {
			System.out.println(user_id);
			System.out.println(pKey.getProductId()+"--"+pKey.getCount());
			try {
				return userService.purchaseByAccount(user, pKey);
			} catch (CountException | BalanceException e) {
				e.printStackTrace();
				return Msg.fail(e.getMessage());
			}
		}else {
			return Msg.fail("该用户不存在!");
		}
	}
	
	@RequestMapping(value = "/purchase_multi_a",method = RequestMethod.POST)
	public Msg purchaseMultiByAccount(@RequestParam("user_id") Integer user_id, @RequestBody List<ProductKey> pKeys) {
		return userService.purchaseMultiByAccount(user_id, pKeys);
	}
	
	@RequestMapping(value = "/save_user",method = RequestMethod.POST)
	public Msg saveUserWithRole(@RequestBody User user,@RequestParam(value = "role_id",defaultValue = "2") Integer role_id) {
		return userService.saveUserWithRole(user, role_id);
	}
	
	@RequestMapping(value = "/save_role",method = RequestMethod.POST)
	public Msg saveRole(@RequestBody Role role) {
		return userService.saveRole(role);
	}
	
	@RequestMapping(value = "/delete_user/{id}",method = RequestMethod.DELETE)
	public Msg deleteUserById(@PathVariable("id") Integer user_id) {
		return userService.deleteUserById(user_id);
	}
	
	@RequestMapping(value = "/delete_role/{id}",method = RequestMethod.DELETE)
	public Msg deleteRoleById(@PathVariable("id") Integer role_id) {
		return userService.deleteRoleById(role_id);
	}
	
	@RequestMapping(value = "/find_u_one",method = RequestMethod.GET)
	public Msg findUserByToken(@RequestHeader(name = "token") String token) {
		return userService.findUserById(token);
	}
	
	@RequestMapping(value = "/find_u_all",method = RequestMethod.GET)
	public Msg findUserAll() {
		return userService.findUserAll();
	}
	
	@RequestMapping(value = "/find_role",method = RequestMethod.GET)
	public Msg findRoleAll() {
		return userService.findRoleAll();
	}
}
