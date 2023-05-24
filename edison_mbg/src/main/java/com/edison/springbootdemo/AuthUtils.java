package com.edison.springbootdemo;

import com.alibaba.fastjson.JSONObject;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.sql.SQLOutput;
import java.util.*;

public class AuthUtils {

    /**
     * 生成签名
     * @param requestBody 请求体json字符串
     * @param clientKey 客户端密钥
     * @return 签名字符串
     */
    public static String generateSignature(String requestBody, String clientKey) throws Exception {
        JSONObject json = JSONObject.parseObject(requestBody);

        // 获取请求参数
        Map<String, String> params = new HashMap<>();
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            if (!entry.getKey().equals("digest")) {
                params.put(entry.getKey(), entry.getValue().toString());
            }
        }

        // 将参数按照key升序排列
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        // 拼接参数值
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(params.get(key));
        }
        String str = sb.toString();
        System.out.println("str="+str);

        // 使用HMAC-SHA256算法计算签名
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(clientKey.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hash = sha256_HMAC.doFinal(str.getBytes());
        return bytesToHex(hash);
    }

    /**
     * 字节数组转十六进制字符串
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 为请求体添加签名字段
     * @param requestBody 请求体json字符串
     * @param clientKey 客户端密钥
     * @return 带有签名字段的请求体json字符串
     */
    public static String signRequestBody(String requestBody, String clientKey) throws Exception {
        String signature = generateSignature(requestBody, clientKey);
        System.out.println("sinature="+signature);
        JSONObject json = JSONObject.parseObject(requestBody);
        json.put("digest", signature);
        return signature;
    }

    public static void main(String[] args) {
        String clientKey = "1c33a3a2a190b798b6e2574376bb3367";
        String clientsecret = "Txzx0215";
        String requestBody = "{\n" +
                "\"adcodes\":\"all\",\n" +
//                " \"clientKey\":\"AMAP-TRAFFIC-BRAIN\",\n" +
                " \"clientKey\":\""+clientKey+"\",\n" +
                "\"size\":\"10\",\n" +
                " \"timestamp\":\"1538018341\",\n" +
                " }";
        System.out.println(requestBody);
        try {
            requestBody = AuthUtils.signRequestBody(requestBody, clientsecret);
            System.out.println(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
