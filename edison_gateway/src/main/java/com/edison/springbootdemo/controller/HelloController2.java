package com.edison.springbootdemo.controller;

import com.edison.springbootdemo.service.IEm_infoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController    //@RestContller返回json格式不能用于页面提取数据，如果需要返回数据给页面则使用@Controller注释
public class HelloController2 {

    @Autowired
    private IEm_infoService em_infoService;

    @GetMapping("/HELLO")
    public String findAll(HttpServletRequest request) throws Exception{
       return "WELCOME";
    }

}