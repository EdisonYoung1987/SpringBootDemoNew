package com.edison.springbootdemo.mapper;

import com.edison.springbootdemo.domain.EmInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
//@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public interface IEm_infoMapper {
    @Select("select * from em_info")
    List<Map<String,Object>> findAll();

    @Select("select * from em_info WHERE em_id=#{emId} for update")
    EmInfo lockOne(@Param("emId") String emId) throws Exception;
}
