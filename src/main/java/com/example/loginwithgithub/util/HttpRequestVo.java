package com.example.loginwithgithub.util;

import lombok.Data;

import java.util.Map;

/**
 * @Author: liujian
 * @Date: 2019/8/7 11:11
 */
@Data
public class HttpRequestVo {
    /**
     * 请求地址
     */
    private String url;

    /**
     * url后跟加的请求参数: x=a&y=b
     */
    private String queryString;

    /**
     * 请求体重的请求参数: key=value
     */
    private Map<String, Object> params;

    /**
     * 返回结果编码设置: 默认UTF-8
     */
    private String charset ="UTF-8";

    /**
     *  请求头设置: key=value
     */
    private Map<String, String> headerMap;

    /**
     * 动态代理设置: 是否启用代理
     */
    private boolean isProxy;

    /**
     * 代理ip
     */
    private String proxyHost;

    /**
     * 代理端口
     */
    private int proxyPort;

}
