package com.edison.springbootdemo.utils.fileOperation;

/**下载文件的config*/
public class FtpConfig {
    private String host;
    private int port;
    private String user;
    private String passwd;
    /**ftp/sftp/http*/
    private String ftpmode;
    private int connectionTimeout;
    private boolean testMode;

    public FtpConfig(String host, int port, String user, String passwd, String ftpmode, boolean testMode) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.passwd = passwd;
        this.ftpmode = ftpmode;
        this.testMode = testMode;
    }

    public FtpConfig(String host, int port, String user, String passwd, String ftpmode) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.passwd = passwd;
        this.ftpmode = ftpmode;
        this.testMode=false;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**ftp/sftp/http*/
    public String getFtpmode() {
        return ftpmode;
    }

    /**ftp/sftp/http*/
    public void setFtpmode(String ftpmode) {
        this.ftpmode = ftpmode;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }
}
