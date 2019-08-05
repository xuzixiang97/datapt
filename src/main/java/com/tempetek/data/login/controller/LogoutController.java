package com.tempetek.data.login.controller;

import com.tempetek.annotation.Record;
import com.tempetek.annotation.RecordType;
import com.tempetek.safety.manager.AuthenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class LogoutController {
	
	@Autowired
	private AuthenManager authenManager;
	
	/**
	 * 退出登录
	 * @return 跳转登录
	 */
	@Record(module = "系统退出", methodType = RecordType.LOGOUT, description = "系统退出")
	@RequestMapping(value="logout", method = RequestMethod.GET)
    public String logout() {
		authenManager.logout();
        return "redirect:login";
    }

}