package com.tempetek.data.config.template.controller;

import com.tempetek.annotation.Record;
import com.tempetek.annotation.RecordType;
import com.tempetek.core.AjaxMessage;
import com.tempetek.core.editor.DateEditor;
import com.tempetek.core.tree.TreeModel;
import com.tempetek.core.tree.TreeNode;
import com.tempetek.core.util.ObjectUtil;
import com.tempetek.data.config.template.entity.TemplateInfo;
import com.tempetek.data.config.template.service.ITemplateService;
import com.tempetek.orm.AbstractBasicController;
import com.tempetek.safety.context.UserContext;
import com.tempetek.safety.manager.AuthenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xuzixiang
 * @date 2019-07-26 09:21:20
 */
@Controller
@RequestMapping(value = "/config/template")
public class TemplateController implements AbstractBasicController {

	@Autowired
	private AuthenManager authenManager;

	@Autowired
	private ITemplateService iTemplateService;

	/**
	 * 跳转模板管理页面
	 * @param model 模型
	 * @param code 菜单编号
	 * @param title 菜单标题
	 * @return 模板管理页面
	 */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(ModelMap model, String code, String title) {
    	model.put("code", code);
    	model.put("title", title);
        return "config/template/list";
    }

	/**
	 * 加载模板信息
	 * 返回tree的节点信息
	 * @return 节点信息
	 */
	@RequestMapping(value = "gettree", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeModel> gettree() {
		List<TemplateInfo> roleInfoList = iTemplateService.findByCondition(null);
		List<TreeModel> treeModelList = new ArrayList<>();

		for (TemplateInfo templateInfo : roleInfoList) {
			TreeModel treeModel = new TreeModel();
			treeModel.setId(templateInfo.getId());
			treeModel.setText(templateInfo.getName());
			treeModel.setPid(0L);
			treeModelList.add(treeModel);
		}
		return TreeNode.getTree(treeModelList);
	}

	/**
	 * 跳转模板新增/修改页面
	 * @param model 模型
	 * @param id 主键ID
	 * @return 模板新增/修改页面路径
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(ModelMap model, Long id) {
		if (id != null) {
			TemplateInfo templateInfo = iTemplateService.findById(id);
			model.put("templateInfo", templateInfo);
		}
		return "config/template/index";
	}
	
	/**
	 * 模板新增/修改
	 * @param condition 模板对象
	 * @return 返回操作结果
	 */
	@Record(module = "模板管理", methodType = RecordType.SAVE, description = "保存模板")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage save(TemplateInfo condition) {
		try {
			TemplateInfo templateInfo = null;
			UserContext userContext = authenManager.getCurrentUser();

			if (condition.getId() != null) {
				templateInfo = iTemplateService.findById(condition.getId());
				ObjectUtil.copyNoClude(condition, templateInfo, new String[]{"creator", "createTime"});
				templateInfo.setModifier(userContext.getUserName());
				templateInfo.setModifiedTime(new Date());
			} else {
				templateInfo = condition;
				templateInfo.setCreator(userContext.getUserName());
				templateInfo.setCreateTime(new Date());
			}

			iTemplateService.save(templateInfo);
			return AjaxMessage.success("保存成功!", templateInfo.getId());
		} catch(Exception e) {
			e.printStackTrace();
			return AjaxMessage.error("保存失败!");
		}
	}
	
	/**
	 * 模板删除
	 * @param id 主键
	 * @return 返回操作结果
	 */
	@Record(module = "模板管理", methodType = RecordType.DEL, description = "删除模板")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage delete(Long id) {
		try {
			iTemplateService.deleteById(id);
			return AjaxMessage.success("删除成功!", null);
		} catch(Exception e) {
			e.printStackTrace();
			return AjaxMessage.error("删除失败!");
		}
	}

	/**
	 * 编码唯一性校验
	 * @param id 主键
	 * @param code 编码
	 * @return 返回检验结果
	 */
	@RequestMapping(value = "isUnique", method = RequestMethod.POST)
	@ResponseBody
	public boolean isUnique(Long id, String code) {
		TemplateInfo templateInfo  = iTemplateService.findByCode(code);
        return(!(templateInfo != null && !templateInfo.getId().equals(id)));
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