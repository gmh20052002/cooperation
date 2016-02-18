package com.wlp.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wlp.api.entity.Order;
import com.wlp.api.entity.Page;
import com.wlp.api.entity.WlpPairLog;

public interface WlpPairLogMapper {
    int deleteByPrimaryKey(String id);

	int insert(WlpPairLog record);

	int insertSelective(WlpPairLog record);

	WlpPairLog selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(WlpPairLog record);

	int updateByPrimaryKey(WlpPairLog record);

	List<WlpPairLog> selectByCondition(
			@Param("condition") WlpPairLog condition,
			@Param("order") Order order, Page<WlpPairLog> page);
}