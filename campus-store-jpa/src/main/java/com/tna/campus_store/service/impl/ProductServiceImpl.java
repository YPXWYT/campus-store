package com.tna.campus_store.service.impl;

import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tna.campus_store.beans.Classification;
import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.Product;
import com.tna.campus_store.beans.StatusEnum;
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
				return Msg.fail("此分类不存在！",StatusEnum.HINT.getCode());
			}
		}else {
			return Msg.fail("该用户不存在！",StatusEnum.HINT.getCode());
		}
	}

	@Override
	public Msg findProductByClassificationId(Integer classification_id) {
		Classification classification = classificationRepository.findOne(classification_id);
		if(classification!=null) {
			return Msg.success("操作成功！").add("classification", classification);
		}else {
			return Msg.fail("此分类不存在！",StatusEnum.HINT.getCode());
		}
	}

	@Override
	public Msg findOne(Integer product_id) {
		Product product = productRepository.findOne(product_id);
		if(product!=null) {
			return Msg.success("操作成功！").add("product", product);
		}else {
			return Msg.fail("该产品不存在！",StatusEnum.HINT.getCode());
		}		
	}

	@Override
	public Msg deleteOne(Integer product_id) {
		productRepository.delete(product_id);
		return Msg.success("操作成功！");
	}

	@Override
	public Msg findAll() {
		List<Product> products = productRepository.findAll();
		if(products!=null) {
			if(!products.isEmpty()) {
				return Msg.success("操作成功！").add("products", products);
			}else {
				return Msg.fail("还没有添加任何东西~",StatusEnum.HINT.getCode());
			}
		}else {
			return Msg.fail("服务器开小差！",StatusEnum.HINT.getCode());
		}		
	}
}
