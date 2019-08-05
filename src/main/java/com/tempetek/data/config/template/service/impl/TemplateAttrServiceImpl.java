package com.tempetek.data.config.template.service.impl;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.template.dao.ITemplateAttrDao;
import com.tempetek.data.config.template.entity.TemplateAttrInfo;
import com.tempetek.data.config.template.service.ITemplateAttrService;
import com.tempetek.orm.AbstractBasicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TemplateAttrServiceImpl extends AbstractBasicServiceImpl<TemplateAttrInfo, Long> implements ITemplateAttrService {
	
    @Autowired
    private ITemplateAttrDao iTemplateAttrDao;
    
    @Override
	public TemplateAttrInfo findById(Long id) {
		return iTemplateAttrDao.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		iTemplateAttrDao.deleteById(id);
	}

	@Override
	public void insert(TemplateAttrInfo condition) {
		iTemplateAttrDao.insert(condition);
	}

	@Override
	public void update(TemplateAttrInfo condition) {
		iTemplateAttrDao.update(condition);
	}
	
	@Override
	public List<TemplateAttrInfo> findAll() {
		return iTemplateAttrDao.findByCondition(new TemplateAttrInfo());
	}
	
	@Override
	public List<TemplateAttrInfo> findByCondition(TemplateAttrInfo condition) {
		return iTemplateAttrDao.findByCondition(condition);
	}

	@Override
	public Pagination<TemplateAttrInfo> findByPager(Pagination<TemplateAttrInfo> pagination, TemplateAttrInfo condition) {
		return iTemplateAttrDao.findByPager(pagination, condition);
	}
	
	@Override
	public void deleteByIds(Long[] ids) {
    	iTemplateAttrDao.deleteByIds(ids);
	}

	@Override
	@Transactional
	public void save(Long templateId, List<TemplateAttrInfo> templateAttrInfos) {
		iTemplateAttrDao.deleteByTemplateId(templateId);

		for(TemplateAttrInfo templateAttrInfo : templateAttrInfos){
			templateAttrInfo.setTemplateId(templateId);
			iTemplateAttrDao.insert(templateAttrInfo);
		}
	}

}