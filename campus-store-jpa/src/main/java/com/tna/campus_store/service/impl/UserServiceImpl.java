package com.tna.campus_store.service.impl;

import java.util.List;
import java.util.UUID;

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
import com.tna.campus_store.beans.Role;
import com.tna.campus_store.beans.TokenMsg;
import com.tna.campus_store.beans.User;
import com.tna.campus_store.exception.BalanceException;
import com.tna.campus_store.exception.CountException;
import com.tna.campus_store.repository.ProductRepository;
import com.tna.campus_store.repository.RoleRepository;
import com.tna.campus_store.repository.UserRepository;
import com.tna.campus_store.service.UserService;
import com.tna.campus_store.utils.RedisUtils;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private ProductRepository productRepository;
	private RedisUtils redisUtils;
	private RoleRepository roleRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RedisUtils redisUtils,
			ProductRepository productRepository,RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.redisUtils = redisUtils;
		this.productRepository = productRepository;
		this.roleRepository = roleRepository;
	}
	
	public Msg loginByAccount(String account,String password) {
		User u1 = null;
		User u2 = null;
		User u3 = null;
		String uuid = null;
		if(((u1 = userRepository.findByAccountAndPassword(account, password))!=null)||
				((u2 = userRepository.findByEmailAndPassword(account, password))!=null)||
				(u3 = userRepository.findByPhoneNumberAndPassword(account, password))!=null) {
			if(u1!=null) {
				uuid = UUID.randomUUID().toString();
				redisUtils.set(uuid, u1.getId(), 7200);
				return TokenMsg.success("登录成功！", uuid).add("user", u1);
			}else if(u2!=null){
				uuid = UUID.randomUUID().toString();
				redisUtils.set(uuid, u2.getId(), 7200);
				return TokenMsg.success("登录成功！", uuid).add("user", u2);
			}else {
				uuid = UUID.randomUUID().toString();
				redisUtils.set(uuid, u3.getId(), 7200);
				return TokenMsg.success("登录成功！", uuid).add("user", u3);
			}
		}else {
			return TokenMsg.fail("登录失败！请检查用户名或密码是否正确！");
		}
	}
	
	public Msg loginByMobilePhone(String verification_code,HttpSession session,String phone_number) {
		String uuid = null;
		if(phone_number!=null) {
			User user = userRepository.findByPhoneNumber(phone_number);
			if(redisUtils.hasKey(verification_code)) {
				redisUtils.del(verification_code);
				if(user!=null) {
					if((user = userRepository.findByPhoneNumber((String) session.getAttribute("phoneNumber")))!=null) {
						uuid = UUID.randomUUID().toString();
						redisUtils.set(uuid, uuid, 7200);
						return TokenMsg.success("登录成功！", uuid).add("user", user);
					}else {
						return TokenMsg.fail("该号码还未注册！").add("user", user);
					}
				}else {
					return Msg.fail("验证失败，该手机号未注册！");
				}
			}else {
				return TokenMsg.fail("验证码输入错误或者已过期！");
			}
		}else {
			return Msg.fail("验证失败，未检测到手机号！");
		}
	}
	
	public Msg registerByMobilePhone(HttpSession session,User user,Integer role_id) {
		String phoneNumber = (String) session.getAttribute("phoneNumber");
		Role role = roleRepository.findOne(role_id);
		if(role!=null) {
			user.setPhoneNumber(phoneNumber);
			user.getRoles().add(role);
			roleRepository.save(role);
			userRepository.save(user);
			return Msg.success("操作成功！");
		}else {
			return Msg.fail("该角色不存在！");
		}
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

	@Override
	@Transactional
	public Msg saveUserWithRole(User user,Integer role_id) {
		Role role = roleRepository.findOne(role_id);
		if(role!=null) {
			user.getRoles().add(role);
			roleRepository.save(role);
			userRepository.save(user);
			return Msg.success("操作成功！");
		}else {
			return Msg.fail("该角色不存在！");
		}
	}

	@Override
	@Transactional
	public Msg saveRole(Role role) {
		roleRepository.save(role);
		return Msg.success("操作成功！");
	}

	@Override
	@Transactional
	public Msg deleteUserById(Integer user_id) {
		userRepository.delete(user_id);
		return Msg.success("操作成功！");
	}

	@Override
	@Transactional
	public Msg deleteRoleById(Integer role_id) {
		roleRepository.delete(role_id);
		return Msg.success("操作成功！");
	}

	@Override
	public Msg findUserById(String token) {
		Integer user_id = (Integer) redisUtils.get(token);
		if(user_id!=null) {
			User user = userRepository.findOne(user_id);
			if(user!=null) {
				return Msg.success("操作成功！").add("user", user);
			}else {
				return Msg.fail("该用户不存在！");
			}
		}else {
			return Msg.fail("服务器错误！");
		}

	}

	@Override
	@Transactional
	public Msg findUserAll() {
		List<User> users = userRepository.findAll();
		if(users!=null) {
			if(!users.isEmpty()) {
				return Msg.success("操作成功！").add("users", users);
			}else {
				return Msg.fail("还没有添加任何用户~");
			}
		}else {
			return Msg.fail("服务器开小差！");
		}	
	}

	@Override
	@Transactional
	public Msg findRoleAll() {
		List<Role> roles = roleRepository.findAll();
		if(roles!=null) {
			if(!roles.isEmpty()) {
				return Msg.success("操作成功！").add("roles", roles);
			}else {
				return Msg.fail("还没有添加任何角色~");
			}
		}else {
			return Msg.fail("服务器开小差！");
		}	
	}
}
