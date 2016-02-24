package com.wlp.core.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wlp.api.entity.WlpWallet;
import com.wlp.api.service.WlpWalletService;
import com.wlp.core.dao.WlpWalletMapper;

@Service
public class WlpWalletServiceImpl implements WlpWalletService {
	@Autowired
	private WlpWalletMapper wlpWalletMapper;

	public WlpWalletMapper getWlpWalletMapper() {
		return wlpWalletMapper;
	}

	public void setWlpWalletMapper(WlpWalletMapper wlpWalletMapper) {
		this.wlpWalletMapper = wlpWalletMapper;
	}

	@Override
	public WlpWallet addWlpWallet(WlpWallet data) {
		data.setId(UUID.randomUUID().toString());
		wlpWalletMapper.insertSelective(data);
		return data;
	}

	@Override
	public WlpWallet updateWlpWallet(WlpWallet data) {
		wlpWalletMapper.updateByPrimaryKeySelective(data);
		return data;
	}

	@Override
	public Long getbalance(String email) {
		WlpWallet condition = new WlpWallet();
		condition.setEmail(email);
		List<WlpWallet> list = wlpWalletMapper.selectByCondition(condition, null, null);
		if(list != null && !list.isEmpty()){
			WlpWallet wlpWallet = list.get(0);
			return wlpWallet.getCapital() + wlpWallet.getBonus();
		}
		return null;
	}

}
