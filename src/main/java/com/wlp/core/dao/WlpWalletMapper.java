package com.wlp.core.dao;

import com.wlp.api.entity.WlpWallet;

public interface WlpWalletMapper {
    int deleteByPrimaryKey(String id);

    int insert(WlpWallet record);

    int insertSelective(WlpWallet record);

    WlpWallet selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WlpWallet record);

    int updateByPrimaryKey(WlpWallet record);
}