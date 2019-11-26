package com.tna.campus_store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tna.campus_store.beans.Classification;
import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.Product;
import com.tna.campus_store.beans.User;
import com.tna.campus_store.repository.ClassificationRepository;
import com.tna.campus_store.repository.ProductRepository;
import com.tna.campus_store.repository.UserRepository;
import com.tna.campus_store.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService{
	private ClassificationRepository classificationRepository;
	private ProductRepository productRepository;
	private UserRepository userRepository;
	@Autowired
	public ProductServiceImpl(ClassificationRepository classificationRepository, ProductRepository productRepository,
			UserRepository userRepository) {
		super();
		this.classificationRepository = classificationRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public Msg saveProductWithClassification(Product product, Integer classification_id,Integer user_id) {
		Classification classification = classificationRepository.findOne(classification_id);
		User user = userRepository.findOne(user_id);
		if(user!=null) {
			if(classification!=null) {
				product.setClassification(classification);
				product.setUser(user);
				classificationRepository.save(classification);
				userRepository.save(user);
				productRepository.save(product);
				return Msg.success("操作成功！");
			}else {
				return Msg.fail("此分类不存在！");
			}
		}else {
			return Msg.fail("该用户不存在！");
		}
	}

	@Override
	public Msg findProductByClassificationId(Integer classification_id) {
		Classification classification = classificationRepository.findOne(classification_id);
		if(classification!=null) {
			return Msg.success("操作成功！").add("classification", classification);
		}else {
			return Msg.fail("此分类不存在！");
		}
	}

}
