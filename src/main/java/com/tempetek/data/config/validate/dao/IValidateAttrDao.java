package com.tempetek.data.config.validate.dao;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.validate.entity.ValidateAttrInfo;
import com.tempetek.orm.AbstractBasicDao;

import java.util.List;

public interface IValidateAttrDao extends AbstractBasicDao<ValidateAttrInfo, Long> {
	
    public List<ValidateAttrInfo> findByCondition(ValidateAttrInfo condition);
    
    public Pagination<ValidateAttrInfo> findByPager(Pagination<ValidateAttrInfo> pagination, ValidateAttrInfo condition);
    
    public void deleteByIds(Long[] ids);

    public void deleteByValidateId(long validateId);

}