package com.edison.springbootdemo;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FlvStreamPlayer {
    public static void main(String[] args) {
        while (true) {
            String flvUrl = "https://nmgsplw.vcmnit.com:10795/flv/f798e48ccc394179ac1963bd069e5e9b_HD.flv?app=livesvr_HD&stream=f798e48ccc394179ac1963bd069e5e9b_HD&port=41936&expired=1684936557&key=f2f0998778fffab763a82a97f6f348279c885c4d"; // FLV 地址

            Scanner scanner = new Scanner(System.in);

            System.out.print("请输入 FLV 地址: ");
            flvUrl = scanner.nextLine();

            try {
                URL url = new URL(flvUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000); // 设置连接超时时间
                connection.setReadTimeout(5000); // 设置读取超时时间

                connection.setRequestProperty("Referer", "http://61.128.209.102/");
                connection.setRequestProperty("token", "asdfasfas");

                connection.connect();


                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 获取响应流
                    InputStream inputStream = connection.getInputStream();
                    // TODO: 这里可以进一步处理 FLV 数据流
                    System.out.println("FLV 可用");
                } else {
                    System.out.println("FLV 不可用，响应码: " + responseCode);
                }
                // 打印请求URL
                System.out.println("请求URL: " + connection.getURL());

                // 打印请求头参数
                System.out.println("请求头参数:");
                for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
                    String name = header.getKey();
                    List<String> values = header.getValue();
                    if (name != null) {
                        for (String value : values) {
                            System.out.println(name + ": " + value);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("连接失败: " + e.getMessage());
            } finally {

            }
        }
    }
}

