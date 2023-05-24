package com.edison.springbootdemo;
import org.apache.commons.net.ftp.*;

public class FTPFileExplorer {
    public static void main(String[] args) {
        String server = "218.29.139.16";
        int port = 21;
        String username = "qx";
        String password = "qx@123";
        String remoteDirectory = "/";

        String remoteDirPath = "/"; // 根目录

        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(server, port);
            ftpClient.login(username, password);

            FTPFile[] files = ftpClient.listFiles(remoteDirPath);
            exploreFiles(files, remoteDirPath, ftpClient);

            ftpClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void exploreFiles(FTPFile[] files, String currentPath, FTPClient ftpClient) throws Exception {
        for (FTPFile file : files) {
            String name = file.getName();
            String fullPath = currentPath + "/" + name;

            if (file.isDirectory()) {
                System.out.println("Directory: " + fullPath);

                // 递归遍历子目录
                FTPFile[] subFiles = ftpClient.listFiles(fullPath);
                exploreFiles(subFiles, fullPath, ftpClient);
            } else {
                System.out.println("File: " + fullPath);
            }
        }
    }
}
