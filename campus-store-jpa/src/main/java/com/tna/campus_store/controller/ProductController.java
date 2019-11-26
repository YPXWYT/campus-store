package com.tna.campus_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.beans.Product;
import com.tna.campus_store.service.ProductService;

/*
 * product/{id}	GET
 * products		GET
 * product/{id}	DELETE
 * product		POST
 * product		PUT
 */
@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public Msg save(@RequestBody Product product, Integer classification_id,Integer user_id) {
		return productService.saveProductWithClassification(product, classification_id, user_id);
	}
	
	@RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
	public Msg deleteOne(@PathVariable("id")Integer product_id) {
		return productService.deleteOne(product_id);
	}
	
	@RequestMapping(value = "/update",method = RequestMethod.PUT)
	public Msg update(@RequestBody Product product,@RequestParam("classification_id") 
	Integer classification_id,@RequestParam("user_id")Integer user_id) {
		return productService.saveProductWithClassification(product, classification_id, user_id);
	}

	@RequestMapping(value = "/find",method = RequestMethod.GET)
	public Msg find() {
		return productService.findAll();
	}
	
	@RequestMapping(value = "/find/{id}",method = RequestMethod.GET)
	public Msg find(@PathVariable("id") Integer product_id) {
		return productService.findOne(product_id);
	}
	
	@RequestMapping(value = "/find/classification_id",method = RequestMethod.GET)
	public Msg findProductByClassificationId(@RequestParam("classification_id") Integer classification_id) {
		return productService.findProductByClassificationId(classification_id);
	}
}
