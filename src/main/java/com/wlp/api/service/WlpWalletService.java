package com.wlp.api.service;

import com.wlp.api.entity.WlpWallet;

public interface WlpWalletService {
	public WlpWallet addWlpWallet(WlpWallet data);

	public WlpWallet updateWlpWallet(WlpWallet data);
	
	public Long getbalance(String email);
}
