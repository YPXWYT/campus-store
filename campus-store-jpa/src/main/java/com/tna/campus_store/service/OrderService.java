package com.tna.campus_store.service;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.Order;

public interface OrderService {
	Msg save(Order order);
	
	Msg findOneByOrderId(Integer order_id);
	
	Msg findOneByUserToken(String token);
	
	Msg delete(Integer order_id);
}
