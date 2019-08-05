package com.tempetek.data.config.template.service;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.template.entity.TemplateInfo;
import com.tempetek.orm.AbstractBasicService;

import java.util.List;

public interface ITemplateService extends AbstractBasicService<TemplateInfo, Long> {

    public TemplateInfo findByCode(String code);
	
	public List<TemplateInfo> findAll();
	
    public List<TemplateInfo> findByCondition(TemplateInfo condition);
    
    public Pagination<TemplateInfo> findByPager(Pagination<TemplateInfo> pagination, TemplateInfo condition);
    
    public void deleteByIds(Long[] ids);

}