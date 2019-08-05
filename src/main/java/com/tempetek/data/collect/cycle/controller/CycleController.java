package com.tempetek.data.collect.cycle.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.tempetek.data.config.validate.entity.ValidateInfo;
import com.tempetek.data.config.validate.service.IValidateService;
import com.tempetek.platform.work.dictionary.entity.DictionaryData;
import com.tempetek.platform.work.dictionary.service.IDictionaryDataService;
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
import com.tempetek.annotation.Record;
import com.tempetek.annotation.RecordType;
import com.tempetek.core.AjaxMessage;
import com.tempetek.core.Pagination;
import com.tempetek.core.editor.DateEditor;
import com.tempetek.core.util.ObjectUtil;
import com.tempetek.orm.AbstractBasicController;
import com.tempetek.data.collect.cycle.entity.CycleInfo;
import com.tempetek.data.collect.cycle.service.ICycleService;

/**
 * 采集周期管理
 * @author xuzixiang
 * @date 2019-08-01 15:05:04
 */
@Controller
@RequestMapping(value = "/collect/cycle")
public class CycleController implements AbstractBasicController {
	
	@Autowired
	private ICycleService iCycleService;

	@Autowired
	private IValidateService iValidateService;

	@Autowired
	private IDictionaryDataService iDictionaryDataService;

	@Autowired
	private AuthenManager authenManager;

	/**
	 * 跳转采集周期管理页面
	 * @param model
	 * @param code 菜单编号
	 * @param title 菜单标题
	 * @return 采集周期管理页面
	 */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(ModelMap model, String code, String title) {
		List<ValidateInfo> validateInfoList = iValidateService.findByCondition(null);
		model.put("validateInfoList", validateInfoList);
    	model.put("code", code);
    	model.put("title", title);
        return "collect/cycle/list";  
    }
    
    /**
	 * 加载采集周期列表
	 * @param model
	 * @param pagination 分页参数
	 * @param condition  查询条件
	 * @return 分页数据集合
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<CycleInfo> list(ModelMap model, Pagination<CycleInfo> pagination, CycleInfo condition) {
		iCycleService.findByPager(pagination, condition);
		for (CycleInfo cycleInfo : pagination.getRows()) {
			this.translate(cycleInfo);
		}
		return pagination;
	}
	
	/**
	 * 跳转采集周期新增/修改页面
	 * @param model
	 * @param id 主键ID
	 * @return 采集周期新增/修改页面路径
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(ModelMap model, Long id) {
		List<CycleInfo> cycleInfoList = iCycleService.findAll();
		List<Long> integerList = new ArrayList<>();

		for(CycleInfo cycleInfo : cycleInfoList){
			integerList.add(cycleInfo.getValidateId());
		}

		int size = integerList.size();
		List<ValidateInfo> validateInfoList  = iValidateService.findNotInIds((Long[])integerList.toArray(new Long[size]));

		if (id != null) {
			CycleInfo cycleInfo = iCycleService.findById(id);
			ValidateInfo validateInfo = iValidateService.findById(cycleInfo.getValidateId());
			validateInfoList.add(validateInfo);
			model.put("cycleInfo", cycleInfo);
		}

		model.put("validateInfoList", validateInfoList);
		return "collect/cycle/index";
	}
	
	/**
	 * 采集周期新增/修改
	 * @param model
	 * @param condition 采集周期对象
	 * @return 返回操作结果
	 */
	@Record(module = "采集周期管理", methodType = RecordType.SAVE, description = "保存采集周期")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage save(ModelMap model, CycleInfo condition) {
		try {
			CycleInfo cycleInfo = null;
			UserContext userContext = authenManager.getCurrentUser();

			if (condition.getId() != null) {
				cycleInfo = iCycleService.findById(condition.getId());
				ObjectUtil.copyNoClude(condition, cycleInfo, new String[]{"creator", "createTime"});
				cycleInfo.setModifier(userContext.getUserName());
				cycleInfo.setModifiedTime(new Date());
			} else {
				cycleInfo = condition;
				cycleInfo.setCreator(userContext.getUserName());
				cycleInfo.setCreateTime(new Date());
			}
			
			iCycleService.save(cycleInfo);
			return AjaxMessage.success("保存成功!", cycleInfo.getId());
		} catch(Exception e) {
			e.printStackTrace();
			return AjaxMessage.error("保存失败!");
		}
	}
	
	/**
	 * 采集周期删除
	 * @param model
	 * @param ids 主键数组
	 * @return 返回操作结果
	 */
	@Record(module = "采集周期管理", methodType = RecordType.DEL, description = "删除采集周期")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxMessage delete(ModelMap model, Long[] ids) {
		try {
			iCycleService.deleteByIds(ids);
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
		if(obj != null){
			CycleInfo cycleInfo = (CycleInfo) obj;

			if(cycleInfo.getValidateId() != null){
				cycleInfo.setValidateStr(iValidateService.findById(cycleInfo.getValidateId()).getName());
			}

			if(cycleInfo.getTimeType() != null){
				DictionaryData dictionaryData = (iDictionaryDataService.findByCode("US_DATA_CYCLE_TIME_TYPE",cycleInfo.getTimeType()));
				cycleInfo.setTimeTypeStr(dictionaryData == null ? "" : dictionaryData.getName());
			}

			if(cycleInfo.getIsEnable() != null){
				DictionaryData dictionaryData = (iDictionaryDataService.findByCode("TP_PARAM_USED",cycleInfo.getIsEnable()));
				cycleInfo.setIsEnableStr(dictionaryData == null ? "" : dictionaryData.getName());
			}
		}
		
	}
	
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
        binder.registerCustomEditor(Date.class, new DateEditor());    
	}
    
}