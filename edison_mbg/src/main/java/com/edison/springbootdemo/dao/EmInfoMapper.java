package com.edison.springbootdemo.dao;

import com.edison.springbootdemo.domain.EmInfo;
import com.edison.springbootdemo.domain.EmInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmInfoMapper {
    long countByExample(EmInfoExample example);

    int deleteByExample(EmInfoExample example);

    int deleteByPrimaryKey(Integer emId);

    int insert(EmInfo record);

    int insertSelective(EmInfo record);

    List<EmInfo> selectByExample(EmInfoExample example);

    EmInfo selectByPrimaryKey(Integer emId);

    int updateByExampleSelective(@Param("record") EmInfo record, @Param("example") EmInfoExample example);

    int updateByExample(@Param("record") EmInfo record, @Param("example") EmInfoExample example);

    int updateByPrimaryKeySelective(EmInfo record);

    int updateByPrimaryKey(EmInfo record);
}