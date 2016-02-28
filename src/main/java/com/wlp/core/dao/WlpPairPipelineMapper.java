package com.wlp.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wlp.api.entity.Order;
import com.wlp.api.entity.Page;
import com.wlp.api.entity.WlpPairPipeline;

public interface WlpPairPipelineMapper {
    int deleteByPrimaryKey(String id);

    int insert(WlpPairPipeline record);

    int insertSelective(WlpPairPipeline record);

    WlpPairPipeline selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WlpPairPipeline record);

    int updateByPrimaryKey(WlpPairPipeline record);

	List<WlpPairPipeline> selectByCondition(
			@Param("condition") WlpPairPipeline condition,
			@Param("order") Order order, Page<WlpPairPipeline> page);
}