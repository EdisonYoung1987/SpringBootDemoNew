package com.edison.springbootdemo;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zm
 * 气象灾害预警
 */
public class DisasterWarning {

    /**
     * 搜索城市
     */
    static String CITY = "黑山县";

    /**
     * 所有城市灾害预警列表
     */
    final static String ALL_CITY_URL = "http://product.weather.com.cn/alarm/grepalarm_cn.php";

    /**
     * 搜索城市灾害预警数据页面
     */
    final static String VALUE_URL = "http://product.weather.com.cn/alarm/webdata/";

    static long TIME_STAMP = System.currentTimeMillis();


    public static void main(String[] args) {
        String url = ALL_CITY_URL + "?_=" + TIME_STAMP;
        String allWarningCity = getWarnCityList(url);
        if (allWarningCity == null) {
            return;
        }
        System.out.println("所有有气象预警城市列表： " + allWarningCity);
        List<List<String>> data = getCityLists(allWarningCity);
        String realUrl = getSearchCityUrl(data);
        if (realUrl == null || "".equals(realUrl)) {
            System.out.println("当前城市没有预警");
        } else {
            String wholeUrl = VALUE_URL + realUrl + "?_=" + TIME_STAMP;
            System.out.println(wholeUrl);
            String htmlContent = dealNewUrl(wholeUrl);
            System.out.println("预警内容： "+htmlContent);
        }
    }

    /**
     * 处理所有城市灾害列表页面
     *
     * @param url
     * @return
     */
    public static String getWarnCityList(String url) {
        String allWeatherInfo = null;
        CloseableHttpClient client;
        client = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet(url);
        HttpResponse response;
        try {
            response = client.execute(get);
            if (response != null) {
                HttpEntity entity = response.getEntity();
                allWeatherInfo = EntityUtils.toString(entity, "UTF-8");
            } else {
                System.out.println("全国所有城市都没有预警或者中国预警网错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allWeatherInfo;
    }

    /**
     * 获得城市列表
     *
     * @param allWeatherInfo
     * @return
     */
    private static List<List<String>> getCityLists(String allWeatherInfo) {
        String[] split = allWeatherInfo.split("=");
        String value = split[1];
        String substring = value.substring(0, value.length() - 1);
        Map<String, List<List<String>>> jsonMap = JSON.parseObject(substring, Map.class);
        return jsonMap.get("data");
    }

    /**
     * 得到搜索城市的url
     *
     * @param data
     * @return
     */
    public static String getSearchCityUrl(List<List<String>> data) {
        String realUrl = "";
        List<List<String>> sortedList = data.stream()
                .filter(strings -> strings.get(0).contains(CITY))
                .sorted(Comparator.comparing(s -> s.get(0).length()))
                .limit(1)
                .collect(Collectors.toList());
        if (sortedList.isEmpty()) {
            return realUrl;
        }
        realUrl = sortedList.get(0).get(1);
        return realUrl;
    }

    /**
     * 访问城市url
     *
     * @param url
     * @return
     */
    public static String dealNewUrl(String url) {
        String htmlContent = "";
        CloseableHttpClient client;
        client = HttpClients.createDefault();

        HttpGet get = new HttpGet(url);
        HttpResponse response;
        try {
            response = client.execute(get);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String string = EntityUtils.toString(entity, "UTF-8");
                System.out.println("搜索城市数据： " + string);
                String[] split = string.split("=");
                String s = split[1];
                Map<String, String> map = JSON.parseObject(s, Map.class);
                htmlContent = map.get("ISSUECONTENT");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlContent;

    }
}

