package com.edison.springbootdemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/*@RunWith(SpringRunner.class)
@SpringBootTest(classes=GatewayApp.class)*/
@Slf4j
public class TestFastDFS2 {
    public static void main(String[]args){
        //上传单个文件
        uploadSingleFile();
        //上传多个文件
        uploadMultipleFiles();
    }
    public static void uploadSingleFile(){
        try {
            //1、得到CloseableHttpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //2、得到HttpPost对象
            HttpPost httppost = new HttpPost("http://127.0.0.1:8080/file/uploadSingleFile");
            //3、得到准备上传文件的完整路径
            File file = new File("C:\\Windows\\Web\\Screen\\img100.jpg");
            FileBody filebody = new FileBody(file);

            //4、把文件封装到HttpEntity对象(1、设定封包模式，浏览器兼容模式2、添加上传的文件3、编译生成）
            HttpEntity entity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addPart("file", filebody)
                    .build();
            //5、把封装好的HttpEntity对象封装到HttpPOST请求
            httppost.setEntity(entity);
            //后端Controller使用@RequestParam接受请求参数和请求体，只支持Content-Type: 为 application/x-www-form-urlencoded编码的内容
            //而http协议如果没赋值Content-Type，则默认application/x-www-form-urlencoded，所以这里不赋值
//            httppost.setHeader("Content-Type","xxxxx");

            //6、执行post请求，获取返回对象HttpResponse
            HttpResponse httpresponse = httpClient.execute(httppost);
            //7、打印返回状态码
            log.info("返回状态码:" + httpresponse.getStatusLine());
            //8、得到内容对象Entity，服务器端是跳转到一个页面的，所以这里打印html标签
            HttpEntity entityreturn = httpresponse.getEntity();
            //9、利用EntityUtils工具类来处理返回的Entity对象，获得字符串
            log.info("返回值:" + EntityUtils.toString(entityreturn));
            //10、关闭连接
            httpClient.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void uploadMultipleFiles(){
        try {
            //1、得到CloseableHttpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //2、得到HttpPost对象
            HttpPost httppost = new HttpPost("http://127.0.0.1:8080/file/uploadMultipleFiles");

            //3、得到准备上传文件的完整路径
            File path=new File("C:\\Windows\\Web\\Screen");
            if(!path.isDirectory()){
                System.err.println("必须为目录");
            }
            String[] files=path.list();//获取文件列表

            //4、把文件封装到HttpEntity对象(1、设定封包模式，浏览器兼容模式2、添加上传的文件3、编译生成）
            //addPart不是map，
            MultipartEntityBuilder builder=MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            for(String file:files){
                builder.addPart("file",new FileBody(new File(path.getPath()+File.separator+file)));
            }
            HttpEntity entity = builder.build();

            //5、把封装好的HttpEntity对象封装到HttpPOST请求
            httppost.setEntity(entity);

            //6、执行post请求，获取返回对象HttpResponse
            HttpResponse httpresponse = httpClient.execute(httppost);
            //7、打印返回状态码
            log.info("返回状态码:" + httpresponse.getStatusLine());
            //8、得到内容对象Entity，服务器端是跳转到一个页面的，所以这里打印html标签
            HttpEntity entityreturn = httpresponse.getEntity();
            //9、利用EntityUtils工具类来处理返回的Entity对象，获得字符串
            log.info("返回值:" + EntityUtils.toString(entityreturn));
            //10、关闭连接
            httpClient.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
