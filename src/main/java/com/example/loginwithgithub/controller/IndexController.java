package com.example.loginwithgithub.controller;

import com.example.loginwithgithub.util.HttpClient;
import com.example.loginwithgithub.util.HttpRequestVo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liujian
 * @Date: 2019/8/6 11:44
 */
@Controller
@Slf4j
public class IndexController {

    /**
     * 客户端id
     */
    private static String clientId = "284077303de65f843231";

    /**
     * 客户端秘钥(一般存储在服务端保证安全)
     */
    private static String clientSecret = "0287cc3d43bd4a6fa60bfa352186949582e16a9b";


    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value = "/index")
    public String home() {
        return "index";
    }


    @RequestMapping(value = "/oauth/redirect")
    public ModelAndView goHome(String code) {
        ModelAndView mode = new ModelAndView();
        //根据拿到的code取得access_token 令牌
        log.info("授权码code={}", code);
        HttpRequestVo pyHttpRequestVo = new HttpRequestVo();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("code", code);
        paramMap.put("client_id", clientId);
        paramMap.put("client_secret", clientSecret);
        pyHttpRequestVo.setParams(paramMap);
        //获取令牌的接口
        pyHttpRequestVo.setUrl("https://github.com/login/oauth/access_token");
        String tokenResponse = HttpClient.doPostByJsonParam(pyHttpRequestVo);
        log.info("tokenResponse------------------------------->{}", tokenResponse);
        String accessToken = "";
        if (!StringUtils.isEmpty(tokenResponse)) {
            String[] strs = tokenResponse.split("&");
            accessToken = strs[0].replace("access_token=", "");
        }

        //获取令牌后向github api请求数据
        HttpRequestVo apiRequest = new HttpRequestVo();
        HashMap<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", "token " + accessToken);
        headMap.put("accept", "application/json");
        apiRequest.setHeaderMap(headMap);
        //获取用户信息
//        apiRequest.setUrl("https://api.github.com/users/momodiy");
        apiRequest.setUrl("https://api.github.com/user");
        String apiResponse = HttpClient.doGet(apiRequest);
        log.info("apiResponse---------------------->{}", apiResponse);

        if (!StringUtils.isEmpty(apiResponse)) {
            Map userInfo = new Gson().fromJson(apiResponse, new TypeToken<Map<String, String>>() {
            }.getType());
            //返回用户信息
            mode.addObject("name", userInfo.get("login"));
        }
        mode.setViewName("welcome");
        return mode;
    }

}
