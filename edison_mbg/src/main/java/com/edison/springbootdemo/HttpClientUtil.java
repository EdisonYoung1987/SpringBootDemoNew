package com.edison.springbootdemo;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class HttpClientUtil {
    public static String clientKey = "1c33a3a2a190b798b6e2574376bb3367";
    public static String clientsecret = "b1bbaebf1857ad7fa906097b4dfdf2d3";

    public static void sendPostRequest(String requestUrl, Map<String, String> headers, String saveFilePath) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
            String redirectUrl = connection.getHeaderField("Location");
            System.out.println("重定向地址="+redirectUrl);
            connection = (HttpURLConnection) new URL(redirectUrl).openConnection();
            // 可以再次设置请求头、超时时间等
        }

        //
        String contentType = connection.getHeaderField("Content-Type");
        System.out.println(contentType);
        if (contentType != null && ( contentType.equals("application/zip") || contentType.equals("application/x-zip-compressed"))){
            try (InputStream inputStream = connection.getInputStream()) {

                Path outputPath = Paths.get(saveFilePath);
                Files.copy(inputStream, outputPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }else {

            try (InputStream inputStream = connection.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }

        responseCode = connection.getResponseCode();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to get response, response code: " + responseCode);
        }

    }

    public static String generateSignature(Map<String, String> paramMap, String secretKey)
            throws Exception {
        // Sort the keys in ascending order
        List<String> sortedKeys = new ArrayList<>(paramMap.keySet());
        Collections.sort(sortedKeys);

        // Concatenate the sorted key-value pairs
        StringBuilder sb = new StringBuilder();
        for (String key : sortedKeys) {
            sb.append(paramMap.get(key));
        }
        String str = sb.toString();
        System.out.println("代签名str="+str);

        // Generate HMAC-SHA256 signature
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(str.getBytes());

        // Convert the HMAC bytes to hexadecimal representation
        StringBuilder signatureBuilder = new StringBuilder();
        for (byte b : hmacBytes) {
            signatureBuilder.append(String.format("%02x", b));
        }

        return signatureBuilder.toString();
    }

    public static String generateUrl(String baseUrl,Map<String,String> parameters){
        StringBuilder sb=new StringBuilder();
        for(String key:parameters.keySet()){
            System.out.println(key);
            System.out.println(parameters.get(key));
            sb.append(key);
            sb.append("=");
            sb.append(parameters.get(key));
            sb.append("&");
        }
        sb.deleteCharAt(sb.length()-1);
        baseUrl=baseUrl+"?"+sb.toString();
        System.out.println(baseUrl);
        return baseUrl;
    }


    public static String getListEventUrl(){
        String baseUrl="https://et-api.amap.com/event/matched/listEvent";
        long time=System.currentTimeMillis()/1000;

        Map<String,String> parameters=new HashMap<>();
        parameters.put("clientKey",clientKey);
        parameters.put("timestamp",""+time);
        parameters.put("adcode","410100");
        parameters.put("type",""+10);

        String signature="";
        try {
            signature = generateSignature(parameters,clientsecret);
            parameters.put("digest",signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        baseUrl=generateUrl(baseUrl,parameters);
        return baseUrl;
    }
    //路况查询
    public static String getRoadTrafficUrl(){
        String baseUrl="https://et-api.amap.com/state/areaJSONPub";
        long time=System.currentTimeMillis()/1000;

        Map<String,String> parameters=new HashMap<>();
        parameters.put("clientKey",clientKey);
        parameters.put("pubname",clientKey);
        parameters.put("timestamp",""+time);
        parameters.put("adcode","410100");

        String signature="";
        try {
            signature = generateSignature(parameters,clientsecret);
            parameters.put("digest",signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        baseUrl=generateUrl(baseUrl,parameters);
        return baseUrl;
    }



    public static void main(String[] args) throws IOException {
//        String url =getListEventUrl();//事件接口
        String url=getRoadTrafficUrl(); //路况查询

        Map<String,String> headers=new HashMap<>();
        HttpClientUtil.sendPostRequest(url, headers,"d:\\tmp\\gd.zip");
    }
}
