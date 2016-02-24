package com.wlp.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wlp.api.entity.Order;
import com.wlp.api.entity.Page;
import com.wlp.api.entity.WlpWallet;

public interface WlpWalletMapper {
    int deleteByPrimaryKey(String id);

	int insert(WlpWallet record);

	int insertSelective(WlpWallet record);

	WlpWallet selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(WlpWallet record);

	int updateByPrimaryKey(WlpWallet record);

	List<WlpWallet> selectByCondition(
			@Param("condition") WlpWallet condition,
			@Param("order") Order order, Page<WlpWallet> page);
}