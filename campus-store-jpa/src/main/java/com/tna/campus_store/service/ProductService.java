package com.tna.campus_store.service;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.Product;

public interface ProductService {
	Msg findProductByClassificationId(Integer classification_id);
	Msg findOne(Integer product_id);
	Msg findAll();
	Msg deleteOne(Integer product_id);
	Msg saveProductWithClassification(Product product, Integer classification_id,Integer user_id);
}
