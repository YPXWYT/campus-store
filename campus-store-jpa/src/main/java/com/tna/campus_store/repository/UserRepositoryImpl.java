package com.tna.campus_store.repository;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tna.campus_store.beans.Order;
import com.tna.campus_store.beans.Product;
import com.tna.campus_store.beans.User;

public class UserRepositoryImpl implements UserRepositoryDefine{

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	@Transactional
	public Order generatorOrder(Product product, User user) {
		Order order = new Order();
		return order;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Order purchaseByAccount(Product product, User user,Integer count) {
		Order order = new Order();
		order.setCreateTime(new Date());
		order.setStatus(0);
		order.setCount(count);
		Double total = product.getSellPrice()*count;
		order.setTotal(total);
		product.setCount(product.getCount()-count);
		user.setMoney(new BigDecimal(user.getMoney()-total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		order.setUser(user);
		order.setProduct(product);
		entityManager.persist(order);
		entityManager.merge(user);
		entityManager.merge(product);
		return order;
	}
}
