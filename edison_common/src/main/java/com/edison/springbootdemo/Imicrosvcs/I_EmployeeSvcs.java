package com.edison.springbootdemo.Imicrosvcs;

import java.util.List;
import java.util.Map;

/**提供雇员操作的微服务接口*/
public interface I_EmployeeSvcs {
    /**打印所有雇员信息*/
    List<Map<String,Object>> findAll() throws Exception;

    void lockOne(String s);
}
