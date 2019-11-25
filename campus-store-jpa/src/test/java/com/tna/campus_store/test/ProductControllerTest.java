package com.tna.campus_store.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tna.campus_store.beans.Product;
import com.tna.campus_store.beans.User;
import com.tna.campus_store.repository.ProductRepository;
import com.tna.campus_store.repository.RoleRepository;
import com.tna.campus_store.repository.UserRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ProductControllerTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void save() {
		Product pro = new Product();
//		pro.setName("英语四级备考教程");
		pro.setName("软件工程备考教程");
		pro.setCreateTime(new Date());
		pro.setCount(10);
		pro.setSellPrice(9.9);
		productRepository.save(pro);
	}
}
