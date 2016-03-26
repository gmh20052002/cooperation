package com.wlp.core.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wlp.api.entity.CommonCst;
import com.wlp.api.entity.Order;
import com.wlp.api.entity.Sort;
import com.wlp.api.entity.WlpPairLog;
import com.wlp.api.entity.WlpPairPipeline;
import com.wlp.api.entity.WlpUser;
import com.wlp.api.entity.WlpWallet;
import com.wlp.api.service.WlpPairLogService;
import com.wlp.core.dao.WlpPairLogMapper;
import com.wlp.core.dao.WlpPairPipelineMapper;
import com.wlp.core.dao.WlpUserMapper;
import com.wlp.core.dao.WlpWalletMapper;

@Service
public class WlpPairLogServiceImpl implements WlpPairLogService {
	@Autowired
	private WlpPairLogMapper wlpPairLogMapper;
	@Autowired
	private WlpPairPipelineMapper wlpPairPipelineMapper;
	@Autowired
	private WlpUserMapper wlpUserMapper;

	@Autowired
	private WlpWalletMapper wlpWalletMapper;

	@Override
	public WlpPairLog addWlpPairLog(WlpPairLog wlpPairLog) {
		wlpPairLog.setId(UUID.randomUUID().toString());
		wlpPairLog.setPairTime(new Date());
		WlpUser fromUser = pairUser(wlpPairLog.getToUser());
		long total=0l;
		Long money=wlpPairLog.getPairMoney();
		wlpPairLog.setFromUser(fromUser.getEmail());
		wlpPairLog.setStatus(CommonCst.NOT_COMPLETE_ORDER);
		long pairMoney = wlpPairLog.getPairMoney();
		WlpWallet condition = new WlpWallet();
		condition.setEmail(wlpPairLog.getToUser());
		List<WlpWallet> from_wlpWallets = wlpWalletMapper.selectByCondition(condition, null, null);
		if (from_wlpWallets != null && !from_wlpWallets.isEmpty()) {// 提供方钱包余额减少
			WlpWallet fromWlpWallet = from_wlpWallets.get(0);
			WlpPairLog condition2=new WlpPairLog();
			condition2.setToUser(wlpPairLog.getToUser());
			condition2.setStatus(CommonCst.NOT_COMPLETE_ORDER);
			condition2.setType(wlpPairLog.getType());
			List<WlpPairLog> wlpPairLog2= wlpPairLogMapper.selectByCondition(condition2, null, null);
			if(wlpPairLog2!=null&&wlpPairLog2.size()>0){
				for(WlpPairLog pairlog:wlpPairLog2){
					money+=pairlog.getPairMoney();
				}			
			}
			if(wlpPairLog.getType()==1){
				total= fromWlpWallet.getBonus();
			}else{
				total=fromWlpWallet.getCapital();
			}
			long fromOldBalance = fromWlpWallet.getCapital() + fromWlpWallet.getBonus();
			if(money>total){
				return null;
			}
			long fromBalance = fromOldBalance- pairMoney;
			wlpPairLog.setToOldBalance(fromOldBalance);
			wlpPairLog.setToBalance(fromBalance);
			wlpPairLogMapper.insertSelective(wlpPairLog);
			return wlpPairLog;
		}
		return null;
	}

	public WlpUser pairUser(String toUserName) {
		List<WlpPairPipeline> pairs = wlpPairPipelineMapper.selectByCondition(null, null, null);
		WlpUser condition = new WlpUser();
		condition.setStatus(CommonCst.ACTIVE);
		Order order = new Order();
		order.setOrderBy("ACTIVE_TIME", Sort.ASC);
		List<WlpUser> initialusers = wlpUserMapper.selectByCondition(condition, order, null);
		List<WlpUser> users=new ArrayList<WlpUser>();
		if (initialusers != null&&initialusers.size()>0) {
			for(WlpUser u:initialusers){
				if(u.getEmail().equals(toUserName)){
					continue;
				}
				users.add(u);
			}
		}
		if (users != null) {
			Set<String> pairsSet = new HashSet<String>();
			if (pairs != null) {
				for (WlpPairPipeline pair : pairs) {
					pairsSet.add(pair.getEmail());
				}
			}
			boolean hasNoLoopUser = false;
			WlpUser result = null;
			for (WlpUser user : users) {
				if (!pairsSet.contains(user.getEmail())) {
					hasNoLoopUser = true;
					result = user;
					WlpPairPipeline record = new WlpPairPipeline();
					record.setId(UUID.randomUUID().toString());
					record.setEmail(user.getEmail());
					wlpPairPipelineMapper.insert(record);
					break;
				}
			}
			if (hasNoLoopUser) {
				return result;
			} else {// 重新开始一轮匹配,取第一个用户匹配
				if (pairs != null) {
					for (WlpPairPipeline pair : pairs) {
						wlpPairPipelineMapper.deleteByPrimaryKey(pair.getId());
					}
				}
				WlpUser user = users.get(0);
				WlpPairPipeline record = new WlpPairPipeline();
				record.setId(UUID.randomUUID().toString());
				record.setEmail(user.getEmail());
				wlpPairPipelineMapper.insert(record);
				return user;
			}
		}
		return null;
	}

	// 这里仅是待确认状态，等接收方确认才是完成
	@Override
	public WlpPairLog completeWlpPairLog(String pairLogId, String orderPic, String desc) {
		WlpPairLog wlpPairLog = wlpPairLogMapper.selectByPrimaryKey(pairLogId);
		if (wlpPairLog != null) {
			wlpPairLog.setOrderTime(new Date());
			wlpPairLog.setOrderPic(orderPic);
			wlpPairLog.setRemark(desc);
			wlpPairLogMapper.updateByPrimaryKeySelective(wlpPairLog);
		}
		return null;
	}

	@Override
	public WlpPairLog sureWlpPairLog(String pairLogId) {
		WlpPairLog wlpPairLog = wlpPairLogMapper.selectByPrimaryKey(pairLogId);
		if (wlpPairLog != null) {

			wlpPairLog.setStatus(CommonCst.COMPLETE_ORDER);
			long pairMoney = wlpPairLog.getPairMoney();
			WlpWallet condition = new WlpWallet();
			condition.setEmail(wlpPairLog.getFromUser());
			List<WlpWallet> from_wlpWallets = wlpWalletMapper.selectByCondition(condition, null, null);
			condition = new WlpWallet();
			condition.setEmail(wlpPairLog.getToUser());
			List<WlpWallet> to_wlpWallets = wlpWalletMapper.selectByCondition(condition, null, null);
			if (from_wlpWallets != null && !from_wlpWallets.isEmpty()) {// 提供方钱包余额增加
				WlpWallet fromWlpWallet = from_wlpWallets.get(0);
				long fromOldBalance = fromWlpWallet.getCapital() + fromWlpWallet.getBonus();
				long fromBalance = fromOldBalance + pairMoney;
				wlpPairLog.setFromOldBalance(fromOldBalance);
				wlpPairLog.setFromBalance(fromBalance);

				// 持久化提供方钱包
				fromWlpWallet.setCapital(fromWlpWallet.getCapital() + pairMoney);
				wlpWalletMapper.updateByPrimaryKeySelective(fromWlpWallet);
			}
			if (to_wlpWallets != null && !to_wlpWallets.isEmpty()) {// 接受方钱包余额减少
				WlpWallet toWlpWallet = to_wlpWallets.get(0);
				long toOldBalance = toWlpWallet.getCapital() + toWlpWallet.getBonus();
				long toBalance = toOldBalance - pairMoney;
				wlpPairLog.setToOldBalance(toOldBalance);
				wlpPairLog.setToBalance(toBalance);

				// 持久化接受方钱包
				toWlpWallet.setCapital(toWlpWallet.getCapital() - pairMoney);
				wlpWalletMapper.updateByPrimaryKeySelective(toWlpWallet);
			}

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

	public WlpPairPipelineMapper getWlpPairPipelineMapper() {
		return wlpPairPipelineMapper;
	}

	public void setWlpPairPipelineMapper(WlpPairPipelineMapper wlpPairPipelineMapper) {
		this.wlpPairPipelineMapper = wlpPairPipelineMapper;
	}

	public WlpUserMapper getWlpUserMapper() {
		return wlpUserMapper;
	}

	public void setWlpUserMapper(WlpUserMapper wlpUserMapper) {
		this.wlpUserMapper = wlpUserMapper;
	}

	public WlpWalletMapper getWlpWalletMapper() {
		return wlpWalletMapper;
	}

	public void setWlpWalletMapper(WlpWalletMapper wlpWalletMapper) {
		this.wlpWalletMapper = wlpWalletMapper;
	}

}
