package com.tempetek.data.config.validate.service;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.validate.entity.ValidateInfo;
import com.tempetek.orm.AbstractBasicService;

import java.util.List;

public interface IValidateService extends AbstractBasicService<ValidateInfo, Long> {
	
	public List<ValidateInfo> findAll();
	
    public List<ValidateInfo> findByCondition(ValidateInfo condition);

    public List<ValidateInfo> findNotInIds(Long[] ids);
    
    public Pagination<ValidateInfo> findByPager(Pagination<ValidateInfo> pagination, ValidateInfo condition);
    
    public void deleteByIds(Long[] ids);
	
}