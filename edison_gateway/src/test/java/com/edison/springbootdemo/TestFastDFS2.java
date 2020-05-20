package com.edison.springbootdemo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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

/*@RunWith(SpringRunner.class)
@SpringBootTest(classes=GatewayApp.class)*/
public class TestFastDFS2 {
    /*@Test
    public void testMutiPart(){*/
public static void main(String[]args){
        uploadInParam();
    }
    public static void uploadInBody(){
        try {
            //1、得到CloseableHttpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //2、得到HttpPost对象
            HttpPost httppost = new HttpPost("http://127.0.0.1:8080/file/uploadFiles");
            //3、得到准备上传文件的完整路径
            File file = new File("C:\\Windows\\Web\\Screen\\img100.jpg");
            FileBody filebody = new FileBody(file);
            //4、把文件封装到HttpEntity对象(1、设定封包模式，浏览器兼容模式2、添加上传的文件3、编译生成）
            HttpEntity entity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addPart("testFile", filebody)
                    .build();
            //5、把封装好的HttpEntity对象封装到HttpPOST请求
            httppost.setEntity(entity);

            //6、执行post请求，获取返回对象HttpResponse
            HttpResponse httpresponse = httpClient.execute(httppost);
            //7、打印返回状态码
            System.out.println("返回状态码:" + httpresponse.getStatusLine());
            //8、得到内容对象Entity，服务器端是跳转到一个页面的，所以这里打印html标签
            HttpEntity entityreturn = httpresponse.getEntity();
            //9、利用EntityUtils工具类来处理返回的Entity对象，获得字符串
            System.out.println("返回值:" + EntityUtils.toString(entityreturn));
            //10、关闭连接
            httpClient.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void uploadInParam(){
        try {
            //1、得到CloseableHttpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //2、得到HttpPost对象

            //3、得到准备上传文件的完整路径
            File file = new File("C:\\Windows\\Web\\Screen\\img100.jpg");

            FileInputStream inputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
            HttpPost httppost = new HttpPost("http://127.0.0.1:8080/file/uploadFiles?"+"multipartFile="+multipartFile.getBytes());
            //4、把文件封装到HttpEntity对象(1、设定封包模式，浏览器兼容模式2、添加上传的文件3、编译生成）

            //5、把封装好的HttpEntity对象封装到HttpPOST请求


            //6、执行post请求，获取返回对象HttpResponse
            HttpResponse httpresponse = httpClient.execute(httppost);
            //7、打印返回状态码
            System.out.println("返回状态码:" + httpresponse.getStatusLine());
            //8、得到内容对象Entity，服务器端是跳转到一个页面的，所以这里打印html标签
            HttpEntity entityreturn = httpresponse.getEntity();
            //9、利用EntityUtils工具类来处理返回的Entity对象，获得字符串
            System.out.println("返回值:" + EntityUtils.toString(entityreturn));
            //10、关闭连接
            httpClient.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
