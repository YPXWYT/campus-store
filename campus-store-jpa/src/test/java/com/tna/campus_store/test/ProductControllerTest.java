package com.tna.campus_store.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tna.campus_store.beans.Classification;
import com.tna.campus_store.beans.Product;
import com.tna.campus_store.repository.ClassificationRepository;
import com.tna.campus_store.repository.ProductRepository;
import com.tna.campus_store.repository.RoleRepository;
import com.tna.campus_store.repository.UserRepository;
import com.tna.campus_store.service.ProductService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ProductControllerTest {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ClassificationRepository classificationRepository;
	@Autowired
	private ProductService productService;
	
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
	
	@Test
	public void saveProductWithClassification() {
		Product product = new Product("软件工程备考教程", 99.0, 9.9, 10, "软件工程备考教程", 0, "图片", new Date(), 3);
		System.out.println(productService.saveProductWithClassification(product, 4,1).getMsg());
	}
	
	@Test
	
	public void saveClassification() {
		List<Classification> classifications = new ArrayList<Classification>();
		Classification classification = new Classification("书籍");
		Classification classification1 = new Classification("文具");
		Classification classification2 = new Classification("电器");
		Classification classification3 = new Classification("体育用品");
		classifications.add(classification3);
		classifications.add(classification2);
		classifications.add(classification1);
		classifications.add(classification);
		classificationRepository.save(classifications);
	}
	
}
