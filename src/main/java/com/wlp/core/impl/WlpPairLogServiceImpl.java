package com.wlp.core.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wlp.api.entity.CommonCst;
import com.wlp.api.entity.Order;
import com.wlp.api.entity.Sort;
import com.wlp.api.entity.WlpPairLog;
import com.wlp.api.service.WlpPairLogService;
import com.wlp.core.dao.WlpPairLogMapper;

@Service
public class WlpPairLogServiceImpl implements WlpPairLogService {
	@Autowired
	private WlpPairLogMapper wlpPairLogMapper;

	@Override
	public WlpPairLog addWlpPairLog(WlpPairLog wlpPairLog) {
		wlpPairLog.setId(UUID.randomUUID().toString());
		wlpPairLog.setPairTime(new Date());
		wlpPairLogMapper.insertSelective(wlpPairLog);
		return wlpPairLog;
	}

	@Override
	public WlpPairLog completeWlpPairLog(String pairLogId, String orderPic) {
		WlpPairLog wlpPairLog = wlpPairLogMapper.selectByPrimaryKey(pairLogId);
		if(wlpPairLog != null){
			wlpPairLog.setOrderTime(new Date());
			wlpPairLog.setOrderPic(orderPic);
			wlpPairLog.setStatus(CommonCst.COMPLETE_ORDER);
			wlpPairLogMapper.updateByPrimaryKeySelective(wlpPairLog);
		}
		return null;
	}

	@Override
	public List<WlpPairLog> getWlpPairLogs(String email, String extrakType) {
		WlpPairLog condition = new WlpPairLog();
		condition.setExtrakType(extrakType);
		condition.setEmail(email);
		Order order = new Order();
		order.setOrderBy("ORDER_TIME", Sort.DESC);
		List<WlpPairLog> result = wlpPairLogMapper.selectByCondition(condition, order, null);
		return result;
	}

	public WlpPairLogMapper getWlpPairLogMapper() {
		return wlpPairLogMapper;
	}

	public void setWlpPairLogMapper(WlpPairLogMapper wlpPairLogMapper) {
		this.wlpPairLogMapper = wlpPairLogMapper;
	}

}
