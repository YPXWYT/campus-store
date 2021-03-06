package com.tna.campus_store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.Order;
import com.tna.campus_store.beans.StatusEnum;
import com.tna.campus_store.repository.OrderRepository;
import com.tna.campus_store.service.OrderService;

public class OrderServiceImpl implements OrderService{

	private OrderRepository orderRepository;
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository) {
		super();
		this.orderRepository = orderRepository;
	}

	@Override
	public Msg findOneByUserTokenAndOrderId(String token,Integer order_id) {
		if(token==null) {
			return Msg.fail("订单号不能为空！",StatusEnum.HINT.getCode());
		}
		if(order_id!=null) {
			Order order = orderRepository.findOne(order_id);
			if(order!=null) {
				return Msg.success("操作成功！").add("order", order);
			}else {
				return Msg.fail("该订单不存在！",StatusEnum.HINT.getCode());
			}
		}else {
			return Msg.fail("订单号不能为空！",StatusEnum.HINT.getCode());
		}		
	}

	@Override
	public Msg deleteByUserTokenAndOrderId(String token,Integer order_id) {
		if(order_id!=null) {
			orderRepository.delete(order_id);
			return Msg.success("操作成功！");
		}else {
			return Msg.fail("订单号不能为空！",StatusEnum.HINT.getCode());
		}
	}

	@Override
	public Msg findOneByUserToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Msg deleteAllByUserToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Msg updateByUserToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
