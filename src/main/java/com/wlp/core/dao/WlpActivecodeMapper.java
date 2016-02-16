package com.wlp.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wlp.api.entity.Order;
import com.wlp.api.entity.Page;
import com.wlp.api.entity.WlpActivecode;

public interface WlpActivecodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(WlpActivecode record);

    int insertSelective(WlpActivecode record);

    WlpActivecode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WlpActivecode record);

    int updateByPrimaryKey(WlpActivecode record);

	List<WlpActivecode> selectByCondition(
			@Param("condition") WlpActivecode condition,
			@Param("order") Order order, Page<WlpActivecode> page);
}