package com.tempetek.data.collect.cycle.dao;

import java.util.List;
import com.tempetek.data.collect.cycle.entity.CycleInfo;
import com.tempetek.core.Pagination;
import com.tempetek.orm.AbstractBasicDao;

public interface ICycleDao extends AbstractBasicDao<CycleInfo, Long> {
	
    public List<CycleInfo> findByCondition(CycleInfo condition);
    
    public Pagination<CycleInfo> findByPager(Pagination<CycleInfo> pagination, CycleInfo condition);
    
    public void deleteByIds(Long[] ids);
	
}