package com.tempetek.data.config.validate.service;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.validate.entity.ValidateAttrInfo;
import com.tempetek.orm.AbstractBasicService;

import java.util.List;

public interface IValidateAttrService extends AbstractBasicService<ValidateAttrInfo, Long> {
	
	public List<ValidateAttrInfo> findAll();
	
    public List<ValidateAttrInfo> findByCondition(ValidateAttrInfo condition);
    
    public Pagination<ValidateAttrInfo> findByPager(Pagination<ValidateAttrInfo> pagination, ValidateAttrInfo condition);
    
    public void deleteByIds(Long[] ids);

    public void save(Long validateId, List<ValidateAttrInfo> validateAttrInfos);
	
}