package com.tna.campus_store.service;

import javax.servlet.http.HttpSession;

import com.tna.campus_store.beans.Msg;

public interface ThirdPartService {

	Msg sendVerificationCode(String phone_number,HttpSession session);
}
