package com.tna.campus_store.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.Order;
import com.tna.campus_store.beans.Product;
import com.tna.campus_store.beans.ProductKey;
import com.tna.campus_store.beans.User;
import com.tna.campus_store.exception.BalanceException;
import com.tna.campus_store.exception.CountException;
import com.tna.campus_store.repository.ProductRepository;
import com.tna.campus_store.repository.UserRepository;
import com.tna.campus_store.service.UserService;
import com.tna.campus_store.utils.RedisUtils;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private ProductRepository productRepository;
	private RedisUtils redisUtils;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RedisUtils redisUtils,
			ProductRepository productRepository) {
		super();
		this.userRepository = userRepository;
		this.redisUtils = redisUtils;
		this.productRepository = productRepository;
	}
	
	public Msg loginByAccount(String account,String password) {
		User u1 = null;
		User u2 = null;
		User u3 = null;
		if(((u1 = userRepository.findByAccountAndPassword(account, password))!=null)||
				((u2 = userRepository.findByEmailAndPassword(account, password))!=null)||
				(u3 = userRepository.findByPhoneNumberAndPassword(account, password))!=null) {
			if(u1!=null) {
				return Msg.success("登录成功！").add("user", u1);
			}else if(u2!=null){
				return Msg.success("登录成功！").add("user", u2);
			}else {
				return Msg.success("登录成功！").add("user", u3);
			}
		}else {
			return Msg.fail("登录失败！请检查用户名或密码是否正确！");
		}
	}
	
	public Msg loginByMobilePhone(String verification_code,HttpSession session) {
		User user = null;
		if(redisUtils.hasKey(verification_code)) {
			redisUtils.del(verification_code);
			if((user = userRepository.findByPhoneNumber((String) session.getAttribute("phoneNumber")))!=null) {
				return Msg.success("登录成功！").add("user", user);
			}else {
				return Msg.fail("该号码还未注册！").add("user", user);
			}			
		}else {
			return Msg.fail("验证码输入错误或者已过期！");
		}
	}
	
	public Msg registerByMobilePhone(HttpSession session,String account,String password) {
		String phoneNumber = (String) session.getAttribute("phoneNumber");
		return Msg.success(phoneNumber);
	}	
	
	public Msg registerByMobilePhoneVerify(String verification_code) {
		if(redisUtils.hasKey(verification_code)) {
			redisUtils.del(verification_code);
			return Msg.success("验证成功！");
		}else {
			return Msg.fail("验证失败，验证码输入错误或者已过期！");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = {
			CountException.class,BalanceException.class
	})
	public Msg purchaseByAccount(User user, ProductKey pKey) throws CountException, BalanceException {
		Product product = productRepository.findOne(pKey.getProductId());
			if(product!=null) {
				if(product.getCount()>0) {
					if(user.getMoney()>=(product.getSellPrice()*pKey.getCount())) {
						Order order = userRepository.purchaseByAccount(product, user,pKey.getCount());
						return Msg.success("购买成功！").add("order", order);
					}else {
						throw new BalanceException("您的余额不足!");
					}
				}else {
					throw new CountException("您来晚啦，该商品已售完!");
				}
			}else {
				return Msg.fail("该商品不存在!");
			}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackFor = {
			CountException.class,BalanceException.class
	})
	public Msg purchaseMultiByAccount(Integer user_id, List<ProductKey> pKeys) {
		User user = userRepository.findOne(user_id);
		if(user!=null) {
			for (ProductKey pKey : pKeys) {
				try {
					System.out.println(purchaseByAccount(user,pKey).getMsg());
				} catch (CountException | BalanceException e) {
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return Msg.fail(e.getMessage());
				}
			}
			return Msg.success("购买成功！");
		}else {
			return Msg.fail("该用户不存在!");
		}
	}
}
