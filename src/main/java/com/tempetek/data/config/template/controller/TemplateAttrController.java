package com.tempetek.data.config.template.controller;

import com.alibaba.fastjson.JSON;
import com.tempetek.core.AjaxMessage;
import com.tempetek.core.editor.DateEditor;
import com.tempetek.data.config.template.entity.TemplateAttrInfo;
import com.tempetek.data.config.template.service.ITemplateAttrService;
import com.tempetek.orm.AbstractBasicController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 模板管理
 * @author xuzixiang
 * @date 2019-07-25 16:48:26
 */
@Controller
@RequestMapping(value = "/config/templateattr")
public class TemplateAttrController implements AbstractBasicController {

	@Autowired
	private ITemplateAttrService iTemplateAttrService;

    /**
	 * 加载模板属性列表
	 * @param template  查询条件
	 * @return 数据集合
	 */
	@RequestMapping(value = "findByTemplateId", method = RequestMethod.POST)
	@ResponseBody
	public List<TemplateAttrInfo> list(Long id, TemplateAttrInfo template) {
		template.setTemplateId(id);
		return iTemplateAttrService.findByCondition(template);
	}

	/**
	 * 模板属性保存
	 * @param templateId 模板id
	 * @param templateAttrJson  保存的对象
	 * @return 返回操作结果
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage save(Long templateId, String templateAttrJson) {
		try {
			List<TemplateAttrInfo> templateAttrInfoList = JSON.parseArray(templateAttrJson,TemplateAttrInfo.class);
			iTemplateAttrService.save(templateId,templateAttrInfoList);
			return AjaxMessage.success("保存成功!", 1);
		} catch(Exception e) {
			e.printStackTrace();
			return AjaxMessage.error("保存失败!");
		}
	}

	/**
	 * 转化数据字典
	 * @param obj 实体类
	 */
	@Override
	public void translate(Object obj) {
	}
	
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
        binder.registerCustomEditor(Date.class, new DateEditor());    
	}
    
}