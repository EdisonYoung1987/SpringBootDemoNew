package com.edison.springbootdemo.dao;

import com.edison.springbootdemo.domain.QrtzLocks;
import com.edison.springbootdemo.domain.QrtzLocksExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QrtzLocksMapper {
    long countByExample(QrtzLocksExample example);

    int deleteByExample(QrtzLocksExample example);

    int deleteByPrimaryKey(@Param("schedName") String schedName, @Param("lockName") String lockName);

    int insert(QrtzLocks record);

    int insertSelective(QrtzLocks record);

    List<QrtzLocks> selectByExample(QrtzLocksExample example);

    int updateByExampleSelective(@Param("record") QrtzLocks record, @Param("example") QrtzLocksExample example);

    int updateByExample(@Param("record") QrtzLocks record, @Param("example") QrtzLocksExample example);
}