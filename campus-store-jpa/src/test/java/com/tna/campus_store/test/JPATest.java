package com.tna.campus_store.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.tna.campus_store.beans.Product;
import com.tna.campus_store.repository.ProductRepository;
import com.tna.campus_store.repository.RoleRepository;
import com.tna.campus_store.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class JPATest {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Test
	@Transactional
	@Rollback(false)
	public void testUser() {
//        User user = new User();
//        user.setName("ypx");
//
//        Role role = roleRepository.findOne(1);
//
//        //配置用户到角色关系，可以对中间表中的数据进行维护     1-1
//        user.getRoles().add(role);
//
//        //配置角色到用户的关系，可以对中间表的数据进行维护     1-1
//        role.getUsers().add(user);
//
//        userRepository.save(user);
//        roleRepository.save(role);
//		userRepository.test();
	}
	
	@Test
	public void testProduct() {
//		productRepository.save(new Product("软件工程师",new Date(),new Date()));
//		productRepository.save(new Product("201742060214",new Date(),new Date()));
	}
	@Test
	public void init() {
		
	}
}
