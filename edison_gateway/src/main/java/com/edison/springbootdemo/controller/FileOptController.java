package com.edison.springbootdemo.controller;

import com.edison.springbootdemo.constant.ResponseConstant;
import com.edison.springbootdemo.domain.Response;
import com.edison.springbootdemo.utils.FastDfsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.csource.fastdfs.StorageClient1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**文件上传的controller，
 * 上传到fastDFS,不提供下载，前端可直接使用http访问文件并下载*/
@Api(tags = "文件传输模块",value ="文件操作")
@RestController    //@RestContller返回json格式不能用于页面提取数据，如果需要返回数据给页面则使用@Controller注释
@RequestMapping("/file")
@Slf4j
public class FileOptController {
    @Resource(name = "storageClient1")
    StorageClient1 storageClient1;

    @ApiOperation(value = "文件上传接口-返回fileId")
    @RequestMapping(value = "/uploadSingleFile",method = RequestMethod.POST)
    public Response uploadSingleFile(HttpServletRequest request, @RequestParam(name = "file") MultipartFile multipartFile){
        log.info(request.getHeader("Content-Type"));

        String fileId=FastDfsUtil.uploadDfsNet(storageClient1,multipartFile);
        if(fileId!=null){

            //网关直接上传文件，这样可以减少网络流量，然后将文件信息和fileId传给service进行保存到数据库或其他操作。
            String orgName=multipartFile.getOriginalFilename();
            log.info("原文件名={},fileId={}",orgName,fileId);

            return Response.success(fileId);
        }

        return Response.error(ResponseConstant.SYSTEM_ERR_CODE.getCode(),"上传文件失败");
    }
    @ApiOperation(value = "文件上传接口-多文件传输返回原文件名-fileId对应关系")
    @RequestMapping(value = "/uploadMultipleFiles",method = RequestMethod.POST)
    public Response uploadMultipleFiles(@RequestParam(name = "file") MultipartFile[] multipartFiles){
        Map<String,String> result=new HashMap<>(8);
        for(MultipartFile multipartFile:multipartFiles) {
            String fileId = FastDfsUtil.uploadDfsNet(storageClient1, multipartFile);
            if (fileId == null) {
                //网关直接上传文件，这样可以减少网络流量，然后将文件信息和fileId传给service进行保存到数据库或其他操作。
                String orgName = multipartFile.getOriginalFilename();
                log.error("原文件名{}上传失败", orgName);
                return Response.error(ResponseConstant.SYSTEM_ERR_CODE.getCode(),"上传文件失败");
            }
            result.put(multipartFile.getOriginalFilename(),fileId);
        }
        return Response.success(result);
    }

}
