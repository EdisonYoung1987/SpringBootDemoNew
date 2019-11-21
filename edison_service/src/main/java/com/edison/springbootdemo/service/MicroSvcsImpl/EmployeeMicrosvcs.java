package com.edison.springbootdemo.service.MicroSvcsImpl;

import com.edison.springbootdemo.Imicrosvcs.I_EmployeeSvcs;
import com.edison.springbootdemo.domain.EmInfo;
import com.edison.springbootdemo.mapper.IEm_infoMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Component
public class EmployeeMicrosvcs implements I_EmployeeSvcs {
    @Autowired
    IEm_infoMapper iEm_infoMapper;

    @Override
    public List<Map<String, Object>> findAll() {
        List<Map<String,Object>> emInfos=iEm_infoMapper.findAll();
        for(Map<String,Object> emInfo: emInfos){
            Set<String> keys=emInfo.keySet();
            for(String key:keys){
                System.out.println(key+"--"+emInfo.get(key));
            }
        }
        return  emInfos;
    }

    @Override
    public void lockOne(String s) {

    }
}
