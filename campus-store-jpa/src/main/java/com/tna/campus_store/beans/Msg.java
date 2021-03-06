package com.tna.campus_store.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回的类
 * 
 * @author lfy
 * 
 */
public class Msg {
	private int code;
	private String msg;
	
	private Map<String, Object> data = new HashMap<String, Object>();

	public static Msg success(String msg){
		Msg result = new Msg();
		result.setCode(2000);
		result.setMsg(msg);
		return result;
	}
	
	public static Msg fail(String msg,Integer code){
		Msg result = new Msg();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}
	
	public Msg add(String key,Object value){
		this.getData().put(key, value);
		return this;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}


	
}
