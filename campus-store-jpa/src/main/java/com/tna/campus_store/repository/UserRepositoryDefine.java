package com.tna.campus_store.repository;

import com.tna.campus_store.beans.Order;
import com.tna.campus_store.beans.Product;
import com.tna.campus_store.beans.User;

public interface UserRepositoryDefine {
	Order generatorOrder(Product product, User user);
	Order purchaseByAccount(Product product, User user,Integer count);
}
