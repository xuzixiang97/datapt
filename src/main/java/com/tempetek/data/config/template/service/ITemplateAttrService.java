package com.tempetek.data.config.template.service;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.template.entity.TemplateAttrInfo;
import com.tempetek.orm.AbstractBasicService;

import java.util.List;

public interface ITemplateAttrService extends AbstractBasicService<TemplateAttrInfo, Long> {
	
	public List<TemplateAttrInfo> findAll();
	
    public List<TemplateAttrInfo> findByCondition(TemplateAttrInfo condition);
    
    public Pagination<TemplateAttrInfo> findByPager(Pagination<TemplateAttrInfo> pagination, TemplateAttrInfo condition);
    
    public void deleteByIds(Long[] ids);

    public void save(Long templateId, List<TemplateAttrInfo> templateAttrInfos);

}