package com.wlp.api.service;

import com.wlp.api.entity.WlpPairLog;

/**
 * 交易记录接口，记录用户的交易流水帐
 * @author 明华
 *
 */
public interface WlpPairLogService {
	/**
	 * 请求帮助
	 * @param wlpPairLog
	 * @return
	 */
	public WlpPairLog addWlpPairLog(WlpPairLog wlpPairLog);
	
}
