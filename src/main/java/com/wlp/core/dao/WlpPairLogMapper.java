package com.wlp.core.dao;

import com.wlp.api.entity.WlpPairLog;

public interface WlpPairLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(WlpPairLog record);

    int insertSelective(WlpPairLog record);

    WlpPairLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WlpPairLog record);

    int updateByPrimaryKey(WlpPairLog record);
}