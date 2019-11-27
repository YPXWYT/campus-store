package com.tna.campus_store.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tna.campus_store.beans.Msg;

/*
 * order/{id}	GET
 * orders		GET
 * order/{id}	DELETE
 * order		POST
 * order		PUT
 * 
 */
@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {
	public Msg findOne(Integer order_id) {
		return null;
	}
}
