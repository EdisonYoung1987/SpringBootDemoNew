package com.edison.springbootdemo;

import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Scanner;

public class FlvProbe {
    public static void main(String[] args) {
        while (true) {
            String flvUrl = "https://nmgsplw.vcmnit.com:10795/flv/f798e48ccc394179ac1963bd069e5e9b_HD.flv?app=livesvr_HD&stream=f798e48ccc394179ac1963bd069e5e9b_HD&port=41936&expired=1684938152&key=5df96dd3437ba5eea7b316fcf7d7817cee05449a"; // FLV 地址
            Scanner scanner = new Scanner(System.in);

            System.out.print("请输入 FLV 地址: ");
            flvUrl = scanner.nextLine();
            Request request = new Request.Builder()
                    .url(flvUrl)
//                    .addHeader("Referer", "http://61.128.209.102/") // 添加自定义的 Header
//                    .addHeader("Referer", "61.128.209.102") // 添加自定义的 Header
                    .build();


//            OkHttpClient client = new OkHttpClient();
            // 创建okHttpClient实例，忽略https证书验证
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .sslSocketFactory(getSSLSocketFactory(),(X509TrustManager) getTrustManager()[0])
                    .hostnameVerifier(getHostnameVerifier())
                    .build();


            Call call=client.newCall(request);
            try (Response response = call.execute()) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    System.out.println("FLV 地址有效，可以获取流媒体数据。");
                } else {
                    System.out.println("FLV 地址无效，无法获取流媒体数据:"+statusCode+" :"+response.message());
                }
            } catch (IOException e) {
                System.out.println("发生异常：" + e.getMessage());
            }
            System.out.println(request.url());
            System.out.println(request.headers().size());
            System.out.println(call.request().headers());
        }
    }
    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static TrustManager[] getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        return trustAllCerts;
    }


}
