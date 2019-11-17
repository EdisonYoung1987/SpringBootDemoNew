package com.edison.springbootdemo.service;

import com.edison.springbootdemo.pojo.Em_info;

import java.util.List;

public interface IEm_infoService {
    List<Em_info> findAll() throws Exception;
    void lockOne(String id);
}
