package com.edison.springbootdemo.controller;

import com.edison.springbootdemo.pojo.Em_info;
import com.edison.springbootdemo.service.IEm_infoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "sysArea",description ="jjj")
@RestController    //@RestContller返回json格式不能用于页面提取数据，如果需要返回数据给页面则使用@Controller注释
@RequestMapping("/em_info")
public class Em_infoController {

    @Autowired
    private IEm_infoService em_infoService;

    @ApiOperation(value = "查询所有的雇员信息", notes="根据url的id来指定更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "/findAll.do",method =RequestMethod.GET)
    public List<Em_info> findAll() throws Exception{
        List<Em_info> em_infoList = em_infoService.findAll();

        return em_infoList;
    }

    @RequestMapping(value = "lockOne")
    public void lockOne(){
        em_infoService.lockOne("1");
    }

}