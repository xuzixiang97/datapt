package com.tempetek.data.config.validate.controller;

import com.alibaba.fastjson.JSON;
import com.tempetek.core.AjaxMessage;
import com.tempetek.core.editor.DateEditor;
import com.tempetek.data.config.validate.entity.ValidateAttrInfo;
import com.tempetek.data.config.validate.service.IValidateAttrService;
import com.tempetek.orm.AbstractBasicController;
import com.tempetek.platform.work.dictionary.entity.DictionaryData;
import com.tempetek.platform.work.dictionary.service.IDictionaryDataService;
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
 * 自检管理
 * @author xuzixiang
 * @date 2019-07-25 16:49:17
 */
@Controller
@RequestMapping(value = "/config/validateattr")
public class ValidateAttrController implements AbstractBasicController {

	@Autowired
	private IValidateAttrService iValidateAttrService;

	@Autowired
	private IDictionaryDataService iDictionaryDataService;

	/**
	 * 加载自检属性列表
	 * @param id  查询条件
	 * @return 数据集合
	 */
	@RequestMapping(value = "findByValidateId", method = RequestMethod.POST)
	@ResponseBody
	public List<ValidateAttrInfo> findByValidateId(Long id, ValidateAttrInfo condition) {
		condition.setValidateId(id);
		List<ValidateAttrInfo> validateAttrInfoList = iValidateAttrService.findByCondition(condition);
		for (ValidateAttrInfo validateAttrInfo : validateAttrInfoList) {
			this.translate(validateAttrInfo);
		}
		return validateAttrInfoList;
	}

	/**
	 * 自检属性保存
	 * @param validateId 模板id
	 * @param validateAttrJson  保存的对象
	 * @return 返回操作结果
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage save(Long validateId, String validateAttrJson) {
		try {
			List<ValidateAttrInfo> templateAttrInfos = JSON.parseArray(validateAttrJson,ValidateAttrInfo.class);
			iValidateAttrService.save(validateId,templateAttrInfos);
			return AjaxMessage.success("保存成功!", 1);
		} catch(Exception e) {
			e.printStackTrace();
			return AjaxMessage.error("保存失败!");
		}
	}
	/**
	 * 字典数据
	 * @return 返回操作结果
	 */
	@RequestMapping(value = "findAllValidateType", method = RequestMethod.POST)
	@ResponseBody
	public List<DictionaryData> findAllValidateType() {
		return iDictionaryDataService.findByDictionaryid(276L);
	}

	
	/**
	 * 转化数据字典
	 * @param obj 实体类
	 */
	@Override
	public void translate(Object obj) {
		if(obj != null){
			ValidateAttrInfo validateAttrInfo = (ValidateAttrInfo) obj;

			if (validateAttrInfo.getValidateType()!=null) {
				DictionaryData dictionaryData = iDictionaryDataService.findByCode("US_DATA_VALIDATE_TYPE", String.valueOf(validateAttrInfo.getValidateType()));
				validateAttrInfo.setValidateTypeStr(dictionaryData == null ? "" : dictionaryData.getName());
			}

		}
	}
	
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
        binder.registerCustomEditor(Date.class, new DateEditor());    
	}
    
}