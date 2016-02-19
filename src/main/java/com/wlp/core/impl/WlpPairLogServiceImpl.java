package com.wlp.core.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wlp.api.entity.CommonCst;
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
		// TODO Auto-generated method stub
		return null;
	}

	public WlpPairLogMapper getWlpPairLogMapper() {
		return wlpPairLogMapper;
	}

	public void setWlpPairLogMapper(WlpPairLogMapper wlpPairLogMapper) {
		this.wlpPairLogMapper = wlpPairLogMapper;
	}

}
