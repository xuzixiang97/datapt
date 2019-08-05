package com.tempetek.data.collect.cycle.service;

import java.util.List;
import com.tempetek.data.collect.cycle.entity.CycleInfo;
import com.tempetek.core.Pagination;
import com.tempetek.orm.AbstractBasicService;

public interface ICycleService extends AbstractBasicService<CycleInfo, Long> {
	
	public List<CycleInfo> findAll();
	
    public List<CycleInfo> findByCondition(CycleInfo condition);
    
    public Pagination<CycleInfo> findByPager(Pagination<CycleInfo> pagination, CycleInfo condition);
    
    public void deleteByIds(Long[] ids);
	
}