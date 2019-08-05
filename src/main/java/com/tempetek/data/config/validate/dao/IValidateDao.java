package com.tempetek.data.config.validate.dao;

import com.tempetek.core.Pagination;
import com.tempetek.data.config.validate.entity.ValidateInfo;
import com.tempetek.orm.AbstractBasicDao;

import java.util.List;

public interface IValidateDao extends AbstractBasicDao<ValidateInfo, Long> {
	
    public List<ValidateInfo> findByCondition(ValidateInfo condition);

    public List<ValidateInfo> findNotInIds(Long[] ids);
    
    public Pagination<ValidateInfo> findByPager(Pagination<ValidateInfo> pagination, ValidateInfo condition);
    
    public void deleteByIds(Long[] ids);
	
}