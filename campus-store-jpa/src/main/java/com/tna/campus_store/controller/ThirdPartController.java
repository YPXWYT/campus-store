package com.tna.campus_store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tna.campus_store.beans.Msg;
import com.tna.campus_store.service.ThirdPartService;

@RestController
@RequestMapping("/third-part")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class ThirdPartController {

	private ThirdPartService thirdPartService;
	
	@Autowired
	public ThirdPartController(ThirdPartService thirdPartService) {
		super();
		this.thirdPartService = thirdPartService;
	}
	
	@RequestMapping("/submail/send")
	public Msg registerByMobilePhoneSendCode(@RequestParam(value = "phone_number")String phone_number,HttpSession session){
		return thirdPartService.sendVerificationCode(phone_number, session);
	}
}
