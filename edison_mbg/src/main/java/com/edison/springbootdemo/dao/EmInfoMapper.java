package com.edison.springbootdemo.dao;

import com.edison.springbootdemo.domain.EmInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

public interface EmInfoMapper {
    int deleteByPrimaryKey(Integer emId);

    int insert(EmInfo record);

    int insertSelective(EmInfo record);

    EmInfo selectByPrimaryKey(Integer emId);

    int updateByPrimaryKeySelective(EmInfo record);

    int updateByPrimaryKey(EmInfo record);
}