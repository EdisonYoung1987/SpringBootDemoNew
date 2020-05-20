package com.edison.springbootdemo.controller;

import com.edison.springbootdemo.domain.Response;
import com.edison.springbootdemo.utils.FastDfsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.csource.fastdfs.StorageClient1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**文件上传的controller，
 * 上传到fastDFS,不提供下载，前端可直接使用http访问文件并下载*/
@Api(tags = "fileOperation",value ="文件操作")
@RestController    //@RestContller返回json格式不能用于页面提取数据，如果需要返回数据给页面则使用@Controller注释
@RequestMapping("/file")
public class FileOptController {
    @Resource(name = "storageClient1")
    StorageClient1 storageClient1;

    @ApiOperation(value = "文件上传接口-支持多文件上传,返回的是原文件名和fileId的对应关系")
    @RequestMapping(value = "/uploadFiles",method = RequestMethod.POST)
    public Response upload(@RequestParam("multipartFile") MultipartFile[] multipartFiles){
        Map<String,String> result=new HashMap<>(8);
        System.out.println(multipartFiles.length);
        for(MultipartFile multipartFile:multipartFiles){
            Map<String,String> map=FastDfsUtil.uploadDfsNet(storageClient1,multipartFile);
            if(map!=null){
                result.putAll(map);
            }
        }
        return Response.success(result);
    }
}
