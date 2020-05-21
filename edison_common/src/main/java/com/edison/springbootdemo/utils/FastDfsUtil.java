package com.edison.springbootdemo.utils;

import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient1;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**fastDFS上传下载工具类，测试类一部分在edison_service的test目录下*/
@Slf4j
public class FastDfsUtil {
    /**上传本地文件到fastDFS
     * fileId-上传成功返回的fileId
     * null -上传失败*/
    public static String uploadDfsLocal(StorageClient1 storageClient1,String localFile){
        NameValuePair[] metaList = new NameValuePair[1];
        metaList[0] = new NameValuePair("fileName", localFile);
        try {
            //获取文件扩展名,如txt jpg
            String ext=null;
            int extInd=localFile.lastIndexOf('.');
            if(extInd!=-1) {
                ext = localFile.substring(extInd+1);//实际上上传后fileId会保存原文件的扩展类型
            }
            String fileId = storageClient1.upload_file1(localFile, ext, metaList);
            return fileId;
        } catch (Exception e) {
            log.error("上传文件"+localFile+"异常：",e);
        }
        return null;
    }

    /**从fastDFS下载文件到本地
     * @param  fileId 上传时获取的fileId，如"group1/M00/00/00/CpY-gF7EwguAQkgJAA9SFlfLF6I080.jpg"
     * @param localFileName 保存到本地重命名的文件名称*/
    public static boolean downloadDfsLocal(StorageClient1 storageClient1,String fileId,String localPath,String localFileName){
        int idx=fileId.indexOf('/');
        if(idx==-1){
            log.error("fileId格式不正确",fileId);
            return false;
        }
        String groupId=fileId.substring(0,idx);
        String fullFileName=fileId.substring(idx+1);
        if(!groupId.matches("group[0-9]")){
            log.error("fileId格式不正确",fileId);
            return false;
        }
        try {
            String[] uploadresult = {groupId, fullFileName};
            byte[] result = storageClient1.download_file(uploadresult[0], uploadresult[1]);

            //本地文件名处理
            if(localFileName==null){
                localFileName=fileId.substring(fileId.lastIndexOf('\\')+1);
            }

            //创建目录
            if(localPath!=null) {
                File path = new File(localPath);
                if (!path.exists()) {
                    path.mkdirs();
                }
                localFileName = path + File.separator + localFileName;
            }
            writeByteToFile(result, localFileName);
            return true;
        }catch (Exception e){
            log.error("下载文件失败",e);
            return false;
        }
    }

    public static void writeByteToFile(byte[] fbyte, String fileName) throws IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = new File(fileName);

        fos = new FileOutputStream(file);
        bos = new BufferedOutputStream(fos);
        bos.write(fbyte);

        if (bos != null) {
            bos.close();
        }
        if (fos != null) {
            fos.close();
        }
    }

    /**将前端提交的文件上传到fastDFS
     * @return map.key-原始文件名称  map.value-上传到fastDfs,如果上传失败-返回null*/
    public static String uploadDfsNet(StorageClient1 storageClient1,MultipartFile multipartFile) {
        try {
            if (multipartFile == null) {
                log.info("文件内容为null");
                return null;
            }
            //得到文件的原始名称
            String originalFilename = multipartFile.getOriginalFilename();
            log.info("开始上传文件:{}",originalFilename);

            //得到文件字节
            byte[] bytes = multipartFile.getBytes();

            //得到文件扩展名
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileId = storageClient1.upload_file1(bytes, ext, null);
            log.info("上传成功");

            return fileId;
        } catch (Exception e) {
            log.error("上传文件异常:", e);
            return null;
        }
    }
}
