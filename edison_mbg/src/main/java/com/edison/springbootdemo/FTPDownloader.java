package com.edison.springbootdemo;

import freemarker.template.utility.DateUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class FTPDownloader {
    private String server;
    private int port;
    private String username;
    private String password;

    public FTPDownloader(String server, int port, String username, String password) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void downloadLatestWeatherFile(String remoteDirectory, String localDirectory,String fileMatch) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 获取远程目录下包含"weather"的所有文件
            String[] files = ftpClient.listNames(remoteDirectory);
            List<String> fileList = Arrays.asList(files);

            Iterator<String> iterator=fileList.iterator();
            List<String> acFiles=new ArrayList<String>();
            while(iterator.hasNext()){
                String file=iterator.next();
                System.out.println(file);
                if(!file.contains(fileMatch)){
                    System.out.println("不处理");
                    continue;
                }
                acFiles.add(file);
            }

            // 根据文件名排序
            Collections.sort(acFiles);

            // 获取序号最大的文件并下载到本地
            if(acFiles.size()>0) {
                String latestFile = acFiles.get(acFiles.size() - 1);
                String remoteFilePath =  latestFile;
                String localFilePath = localDirectory + "/" + latestFile;
                System.out.println(remoteFilePath);
                System.out.println(localFilePath);

                try (OutputStream outputStream = new FileOutputStream(localFilePath)) {
                    boolean success = ftpClient.retrieveFile(remoteFilePath, outputStream);
                    if (success) {
                        System.out.println("File downloaded successfully: " + latestFile);
                    } else {
                        System.out.println("File download failed: " + latestFile);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        }
    }

    public static void main(String[] args) {
        String server = "218.29.139.16";
        int port = 21;
        String username = "qx";
        String password = "qx@123";
        String remoteDirectory = "/";
        String localDirectory = "d://tmp";

        FTPDownloader ftpDownloader = new FTPDownloader(server, port, username, password);
        //下载天气文件-最新的那个
        try {
            System.out.println("下载天气文件");
            String fileMatch="TRHWYMEF_HENAN_"+ "20230508";
            ftpDownloader.downloadLatestWeatherFile(remoteDirectory, localDirectory,fileMatch);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //下载气象预警文件-每天只有一个
        try {
            System.out.println("下载预警文件");
            String fileMatch="warn"+ "20230515";
            ftpDownloader.downloadLatestWeatherFile("/yj", localDirectory,fileMatch);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
