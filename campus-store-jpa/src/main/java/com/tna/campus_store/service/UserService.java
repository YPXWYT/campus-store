package com.tna.campus_store.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.ProductKey;
import com.tna.campus_store.beans.Role;
import com.tna.campus_store.beans.User;
import com.tna.campus_store.exception.BalanceException;
import com.tna.campus_store.exception.CountException;

public interface UserService {

	Msg loginByAccount(String account,String password);
	
	Msg loginByMobilePhone(String verification_code,HttpSession session,String phone_number);
	
	Msg registerByMobilePhone(HttpSession session,User user,Integer role_id);
	
	Msg registerByMobilePhoneVerify(String verification_code);
	
	Msg purchaseByAccount(User user, ProductKey pKey) throws CountException, BalanceException;
	
	Msg purchaseMultiByAccount(Integer user_id, List<ProductKey> pKeys);
	
	Msg saveUserWithRole(User user,Integer role_id);
	
	Msg saveRole(Role role);
	
	Msg deleteUserById(Integer user_id);
	
	Msg deleteRoleById(Integer role_id);
	
	Msg findUserById(String token);
	
	Msg findUserAll();
		
	Msg findRoleAll();
}
