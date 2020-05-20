package com.edison.springbootdemo;

import com.edison.springbootdemo.config.async.AsyncExecutor;
import com.edison.springbootdemo.service.MicroSvcsImpl.EmployeeMicrosvcs;
import com.edison.springbootdemo.utils.DistributeLocker;
import com.edison.springbootdemo.utils.FastDfsUtil;
import org.csource.fastdfs.StorageClient1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServiceApp.class)
public class FastdfsTest {
    private static final String LOCAL_FILE="C:\\Windows\\Web\\Screen\\img102.jpg";

    @Resource(name="storageClient1")
    private StorageClient1 storageClient1;

    @Test
    public void testUploadLocal(){
        long start=System.currentTimeMillis();
        String fileId=FastDfsUtil.uploadDfsLocal(storageClient1,LOCAL_FILE);
        long end=System.currentTimeMillis();
        if(fileId==null){
            System.out.println("上传失败");
        }else{
            System.out.println("耗时"+(end-start)+"毫秒，返回fileId:"+fileId);
        }
    }
    @Test
    public void testDownloadLocal(){
        long start=System.currentTimeMillis();
        boolean res=FastDfsUtil.downloadDfsLocal(storageClient1,"group1/M00/00/00/CpY-gF7EwguAQkgJAA9SFlfLF6I080.jpg",
                "D:\\tmp\\fdfs\\download","abcd.jpg");
        long end=System.currentTimeMillis();
        if(!res){
            System.out.println("上传失败");
        }else{
            System.out.println("耗时"+(end-start)+"毫秒");
        }
    }

}
