package com.edison.springbootdemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**端口扫描*/
public class PortDetectUtil {
    public static void main(String[] args) {
        String host="183.56.136.34";
        ThreadPoolExecutor executor=new ThreadPoolExecutor(100,150,10000, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(2000));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        for(int port=80;port<1025;port++){
            DetectGatewayTask task=new DetectGatewayTask(host,port);
            executor.submit(task);
        }

        executor.shutdown();

    }
}

    class DetectGatewayTask implements Callable<Boolean> {
        private String host;
        private int port;
        public DetectGatewayTask(String host, int port){
            this.host=host;
            this.port=port;
        }

        @Override
        public Boolean call() throws Exception {
            Socket socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(host, port),1500);//建立连接，超时时间
                System.out.println(port+"  端口开启");
            } catch (IOException e) {
                if("connect timed out".equals(e.getMessage())) {
                    System.out.println(port+"  端口超时");
                    return false;
                }else{
                    System.out.println(port+"  端口不通："+e.getMessage());
                }

            } finally {
                try {
                    socket.close();
                } catch (IOException e) { }
            }
            return true;
        }
    }
