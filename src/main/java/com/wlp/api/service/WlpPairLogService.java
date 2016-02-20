package com.wlp.api.service;

import java.util.List;

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
	
	/**
	 * 完成交易
	 * @param wlpPairLog
	 * @return
	 */
	public WlpPairLog completeWlpPairLog(String pairLogId, String pairPic);
	
	/**
	 * 查询用户钱包流水帐
	 * @param email
	 * @param extrakType dynamic--互助奖钱包明细,help--动态奖钱包明细
	 * @return
	 */
	public List<WlpPairLog> getWlpPairLogs(String email, String extrakType);
}
