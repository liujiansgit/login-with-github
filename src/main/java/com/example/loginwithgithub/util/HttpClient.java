package com.example.loginwithgithub.util;

import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.util.UriUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujian
 * @Date: 2019/8/7 11:12
 */
public class HttpClient {

    /**
     * get请求
     * @param requestVo
     * @return
     */
    public static String doGet(HttpRequestVo requestVo) {

        String response = null;
        String queryString = requestVo.getQueryString();
        String url = requestVo.getUrl();
        Map<String, String> headerMap = requestVo.getHeaderMap();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            url = (queryString == null ? url : url + "?" + UriUtils.encodeQuery(queryString, "UTF-8"));
            httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            // 建造器: 设置请求和传输超时时间
            RequestConfig.Builder custom = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000);
            RequestConfig requestConfig = null;
            // 需要动态代理
            if (requestVo.isProxy()) {
                String proxyHost = requestVo.getProxyHost();
                int proxyPort = requestVo.getProxyPort();
                HttpHost proxy = new HttpHost(proxyHost, proxyPort);
                requestConfig = custom.setProxy(proxy).build();
            } else {
                requestConfig = custom.build();
            }
            httpGet.setConfig(requestConfig);
            // header参数设置
            if (headerMap != null && !headerMap.isEmpty()) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpGet.setHeader(key, value);
                }
            }
            // 执行
            httpResponse = httpClient.execute(httpGet);
            // 响应编码
            response = EntityUtils.toString(httpResponse.getEntity(), requestVo.getCharset());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 表单参数 post请求(不支持json)
     * @param requestVo
     * @return
     */
    public static String doPost(HttpRequestVo requestVo) {

        String response = null;
        String queryString = requestVo.getQueryString();
        String url = requestVo.getUrl();
        Map<String, String> headerMap = requestVo.getHeaderMap();
        Map<String, Object> params = requestVo.getParams();
        String charset = requestVo.getCharset();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            url = (queryString == null ? url : url + "?" + UriUtils.encodeQuery(queryString, "UTF-8"));
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            // 建造器: 设置请求和传输超时时间
            RequestConfig.Builder custom = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000);
            RequestConfig requestConfig = null;
            // 需要动态代理
            if (requestVo.isProxy()) {
                String proxyHost = requestVo.getProxyHost();
                int proxyPort = requestVo.getProxyPort();
                HttpHost proxy = new HttpHost(proxyHost, proxyPort);
                requestConfig = custom.setProxy(proxy).build();
            } else {
                requestConfig = custom.build();
            }
            httpPost.setConfig(requestConfig);
            // header参数设置
            if (headerMap != null && !headerMap.isEmpty()) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpPost.setHeader(key, value);
                }
            }
            // body参数设置
            if (params != null && params.size() > 0) {
                List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    pairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(pairList, "UTF-8"));
            }
            // 执行
            httpResponse = httpClient.execute(httpPost);
            // 响应编码
            response = EntityUtils.toString(httpResponse.getEntity(), charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * json参数 post请求（待测试）
     * @param requestVo
     * @return
     */
    public static String doPostByJsonParam(HttpRequestVo requestVo) {

        String response = null;
        String queryString = requestVo.getQueryString();
        String url = requestVo.getUrl();
        Map<String, String> headerMap = requestVo.getHeaderMap();
        Map<String, Object> params = requestVo.getParams();
        String charset = requestVo.getCharset();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        try {
            url = (queryString == null ? url : url + "?" + UriUtils.encodeQuery(queryString, "UTF-8"));
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            // 建造器: 设置请求和传输超时时间
            RequestConfig.Builder custom = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000);
            RequestConfig requestConfig = null;
            // 需要动态代理
            if (requestVo.isProxy()) {
                String proxyHost = requestVo.getProxyHost();
                int proxyPort = requestVo.getProxyPort();
                HttpHost proxy = new HttpHost(proxyHost, proxyPort);
                requestConfig = custom.setProxy(proxy).build();
            } else {
                requestConfig = custom.build();
            }
            httpPost.setConfig(requestConfig);
            // header参数设置
            if (headerMap != null && !headerMap.isEmpty()) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpPost.setHeader(key, value);
                }
            }
            // body参数设置
            if (params != null && params.size() > 0) {
                StringEntity entity = new StringEntity(new Gson().toJson(params), ContentType.APPLICATION_JSON);
                // 发送Json格式的数据请求
                httpPost.setEntity(entity);
            }
            // 执行
            httpResponse = httpClient.execute(httpPost);
            // 响应编码
            response = EntityUtils.toString(httpResponse.getEntity(), charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
