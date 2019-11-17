package com.edison.springbootdemo.service;

import com.edison.springbootdemo.mapper.IEm_infoMapper;
import com.edison.springbootdemo.pojo.Em_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class Em_infoServiceImpl implements IEm_infoService{

    @Autowired
    private IEm_infoMapper em_infoMapper;
    @Override
    public List<Em_info> findAll() throws Exception {
        return em_infoMapper.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED )
    public void lockOne(String id)  {
        try {
            em_infoMapper.lockOne("1");
            System.out.println("已经锁住30秒"+new SimpleDateFormat(""));

            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束");
        return ;
    }
}
