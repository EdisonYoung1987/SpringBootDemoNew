package com.edison.springbootdemo.utils.fileOperation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpFileDownloader implements FileDownloader {
    @Override
    public boolean isSupport(String mode) {
        return "HTTP".matches(mode.toUpperCase());
    }

    @Override
    public void download(String fileName, String localPath, FtpConfig ftpConfig) throws IOException  {
        URL url=new URL(ftpConfig.getHost());
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        connection.setConnectTimeout(ftpConfig.getConnectionTimeout());
//        connection.set

        InputStream inputStream=connection.getInputStream();


    }
}
