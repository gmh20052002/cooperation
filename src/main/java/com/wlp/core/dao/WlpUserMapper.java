package com.wlp.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wlp.api.entity.Order;
import com.wlp.api.entity.Page;
import com.wlp.api.entity.WlpUser;

public interface WlpUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(WlpUser record);

    int insertSelective(WlpUser record);

    WlpUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WlpUser record);

    int updateByPrimaryKey(WlpUser record);

	List<WlpUser> selectByCondition(
			@Param("condition") WlpUser condition,
			@Param("order") Order order, Page<WlpUser> page);
}