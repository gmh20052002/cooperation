package com.wlp.core.dao;

import com.wlp.api.entity.WlpActivecode;

public interface WlpActivecodeMapper {
    int deleteByPrimaryKey(String id);

    int insert(WlpActivecode record);

    int insertSelective(WlpActivecode record);

    WlpActivecode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WlpActivecode record);

    int updateByPrimaryKey(WlpActivecode record);
}