package com.tempetek.data.login.controller;

import com.alibaba.fastjson.JSON;
import com.tempetek.core.constants.CommConstants;
import com.tempetek.core.tree.TreeModel;
import com.tempetek.platform.auth.menu.service.IMenuService;
import com.tempetek.platform.organization.person.entity.Person;
import com.tempetek.safety.context.UserContext;
import com.tempetek.safety.manager.AuthenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class LoginController{
	
	@Autowired
	private AuthenManager authenManager;
	
	@Autowired
	private IMenuService iMenuService;
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(ModelMap model){
		return "login";
	}
	
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(ModelMap model) {
		UserContext userContext = authenManager.getCurrentUser();
		List<TreeModel> treeModelList = new ArrayList<>();
		
		if(userContext!=null){
			Person person = userContext.getPerson();
			TreeModel treeModel = iMenuService.findMenuByUserid(userContext.getId(), CommConstants.ROOT_FUNCTION);
			
			if(treeModel!=null&&treeModel.getChildren()!=null){
				treeModelList.addAll(treeModel.getChildren());
			}
			
			model.put("menuList", JSON.toJSONString(treeModelList));
			model.put("name", person==null?"":person.getName());
			model.put("userid", userContext.getId());
		}else{
			model.put("name", "");
		}
		return "main";
	}
	
}