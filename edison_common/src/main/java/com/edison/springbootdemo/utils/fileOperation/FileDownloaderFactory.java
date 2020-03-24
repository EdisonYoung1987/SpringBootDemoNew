package com.edison.springbootdemo.utils.fileOperation;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FileDownloaderFactory {
    private static List<FileDownloader> list=new ArrayList<>();
    static {
        list.add(new FtpFileDownloader());
        list.add(new HttpFileDownloader());
    }

    FileDownloader getFileDownloader(String mode){
        for(FileDownloader fileDownloader:list){
            if(fileDownloader.isSupport(mode)) {
                return fileDownloader;
            }
        }
        return null;
    }
}
