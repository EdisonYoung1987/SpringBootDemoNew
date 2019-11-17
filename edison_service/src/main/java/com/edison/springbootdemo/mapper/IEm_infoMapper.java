package com.edison.springbootdemo.mapper;

import com.edison.springbootdemo.pojo.Em_info;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IEm_infoMapper {
    @Select("select * from em_info")
    List<Em_info> findAll() throws Exception;

    @Select("select * from em_info WHERE em_id=#{emId} for update")
    Em_info lockOne(@Param("emId") String emId) throws Exception;
}
