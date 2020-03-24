package com.edison.springbootdemo.utils.fileOperation;

import java.io.IOException;
import java.net.MalformedURLException;

public interface FileDownloader {
    boolean isSupport(String mode);
    void download(String fileName,String localPath,FtpConfig ftpConfig) throws IOException;
}
