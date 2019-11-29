package com.tna.campus_store.service;

import com.tna.campus_store.beans.Msg;

public interface OrderService {
	
	/**
	 * 通过订单id删除订单
	 * 只有用户自己或者管理员需要时可以删除订单
	 * 但并非真正删除数据，而是通过改变订单的状态
	 * @param token	用户的授权信息
	 * @param order_id	订单号
	 * @return
	 */
	Msg deleteByUserTokenAndOrderId(String token,Integer order_id);
	/**
	 * 删除用户所有订单
	 * 只有用户自己或者管理员需要时可以删除订单
	 * 但并非真正删除数据，而是通过改变订单的状态
	 * @param token	用户的授权信息
	 * @return
	 */
	Msg deleteAllByUserToken(String token);
	/**
	 * 通过用户的授权信息更新用户的信息
	 * @param token	用户的授权信息
	 * @return
	 */
	Msg updateByUserToken(String token);
	/**
	 * 通过用户的授权信息订单号查询订单信息
	 * @param token	用户的授权信息
	 * @param order_id	订单号
	 * @return
	 */
	Msg findOneByUserTokenAndOrderId(String token,Integer order_id);
	/**
	 * 通过用户的授权信息查询用户的订单信息
	 * @param token	用户的授权信息
	 * @return
	 */
	Msg findOneByUserToken(String token);
}
