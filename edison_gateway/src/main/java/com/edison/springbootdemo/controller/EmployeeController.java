package com.edison.springbootdemo.controller;

import com.edison.springbootdemo.Imicrosvcs.I_EmployeeSvcs;
import com.edison.springbootdemo.constant.ResponseConstant;
import com.edison.springbootdemo.domain.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "sysArea",description ="jjj")
@RestController    //@RestContller返回json格式不能用于页面提取数据，如果需要返回数据给页面则使用@Controller注释
@RequestMapping("/employee")
public class EmployeeController {

    @Reference //dubbo远程调用
    private I_EmployeeSvcs employeeSvcs;

    @ApiOperation(value = "查询所有的雇员信息", notes="根据url的id来指定更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "/findall",method =RequestMethod.GET)
    public Response findAll() throws Exception{
        Response response=new Response();

        List<Map<String,Object>> em_infoList = null;
        try {
            em_infoList = employeeSvcs.findAll();
        } catch (RpcException e) {
            System.out.println("远程服务调用失败，检查是否启动该远程服务");
            response.setStatusCode(ResponseConstant.REMOTE_SERVICE_UNAVAILABLE.getCode());
            response.setRetMessage(ResponseConstant.REMOTE_SERVICE_UNAVAILABLE.getMessage());
            return response;
        }

        response.setStatusCode(ResponseConstant.SUCC_CODE.getCode());
        response.setRetMessage(ResponseConstant.SUCC_CODE.getMessage());
        response.setData(em_infoList);
        return response;
    }

    @RequestMapping(value = "lockone")
    public void lockOne(){
        employeeSvcs.lockOne("1");
    }

}