package com.tempetek.data.config.validate.controller;

import com.tempetek.annotation.Record;
import com.tempetek.annotation.RecordType;
import com.tempetek.core.AjaxMessage;
import com.tempetek.core.editor.DateEditor;
import com.tempetek.core.tree.TreeModel;
import com.tempetek.core.tree.TreeNode;
import com.tempetek.core.util.ObjectUtil;
import com.tempetek.data.config.template.entity.TemplateInfo;
import com.tempetek.data.config.template.service.ITemplateService;
import com.tempetek.data.config.validate.entity.ValidateInfo;
import com.tempetek.data.config.validate.service.IValidateService;
import com.tempetek.orm.AbstractBasicController;
import com.tempetek.safety.context.UserContext;
import com.tempetek.safety.manager.AuthenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * 自检管理
 * @author xuzixiang
 * @date 2019-07-25 16:49:36
 */
@Controller
@RequestMapping(value = "/config/validate")
public class ValidateController implements AbstractBasicController {

	@Autowired
	private IValidateService iValidateService;

	@Autowired
	private ITemplateService iTemplateService;

	@Autowired
	private AuthenManager authenManager;

	@Value("${dict.validate.type}")
	private String VALIDATE_TYPE;

	@Value("${dict.column.null}")
	private String COLUMN_NULL;

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
        return "config/validate/list";
    }

    /**
	 * 加载自检列表
	 * @param condition  查询条件
	 * @return 树数据
	 */
	@RequestMapping(value = "gettree", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeModel> loadtree(ValidateInfo condition) {
		List<ValidateInfo> allValidateInfoList = iValidateService.findByCondition(condition);
		List<TreeModel> treeModelList = new ArrayList<>();

		for (ValidateInfo validateInfo : allValidateInfoList) {
			TreeModel treeModel = new TreeModel();
			treeModel.setAttribute(validateInfo);
			treeModel.setId(validateInfo.getId());
			treeModel.setPid(0L);
			treeModel.setText(validateInfo.getName());
			treeModelList.add(treeModel);
		}
		return TreeNode.getTree(treeModelList);
	}

	/**
	 * 跳转自检新增/修改页面
	 * @param model 模型
	 * @param id 主键ID
	 * @return 自检新增/修改页面路径
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(ModelMap model, Long id) {
        List<TemplateInfo> templateInfoList = iTemplateService.findByCondition(null);
        model.put("templateInfoList", templateInfoList);
        if (id != null) {
			ValidateInfo validateInfo = iValidateService.findById(id);
			translate(validateInfo);
			model.put("validateInfo", validateInfo);
		}
		return "config/validate/index";
	}
	
	/**
	 * 自检新增/修改
	 * @param condition 自检对象
	 * @return 返回操作结果
	 */
	@Record(module = "自检管理", methodType = RecordType.SAVE, description = "保存自检")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage save(ValidateInfo condition) {
		try {
			ValidateInfo validateInfo;
			UserContext userContext = authenManager.getCurrentUser();

			if (condition.getId() != null) {
				validateInfo = iValidateService.findById(condition.getId());
				ObjectUtil.copyNoClude(condition, validateInfo, new String[] {"creator", "createTime"});
				validateInfo.setModifier(userContext.getUserName());
				validateInfo.setModifiedTime(new Date());
			} else {
				validateInfo = condition;
				validateInfo.setCreateTime(new Date());
				validateInfo.setCreator(userContext.getUserName());
			}
			
			iValidateService.save(validateInfo);
			return AjaxMessage.success("保存成功!", validateInfo.getId());
		} catch(Exception e) {
			e.printStackTrace();
			return AjaxMessage.error("保存失败!");
		}
	}

	/**
	 * 自检删除
	 * @param id 主键
	 * @return 返回操作结果
	 */
	@Record(module = "自检管理", methodType = RecordType.DEL, description = "删除自检")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage deleteById(Long id) {
		try {
			iValidateService.deleteById(id);
			return AjaxMessage.success("删除成功!", null);
		} catch(Exception e) {
			e.printStackTrace();
			return AjaxMessage.error("删除失败!");
		}
	}

	/**
	 * 转化数据字典
	 * @param obj 实体类
	 */
	@Override
	public void translate(Object obj) {
		if (obj != null) {
            ValidateInfo validateInfo = (ValidateInfo) obj;

            if (validateInfo.getTemplateId() != null) {
                validateInfo.setTemplateStr(iTemplateService.findById(validateInfo.getTemplateId()).getName());
            }
		}
	}
	
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
        binder.registerCustomEditor(Date.class, new DateEditor());    
	}
    
}