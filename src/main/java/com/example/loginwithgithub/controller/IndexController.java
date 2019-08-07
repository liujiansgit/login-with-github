package com.example.loginwithgithub.controller;

import com.pystandard.webutils.client.PyHttpClient;
import com.pystandard.webutils.client.PyHttpRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

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
    private static String clientId = "7e015d8ce32370079895";

    /**
     * 客户端秘钥(一般存储在服务端保证安全)
     */
    private static String clientSecret = "2b976af0e6b6ceea2b1554aa31d1fe94ea692cd9";

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
        log.info("code=" + code);
        PyHttpRequestVo pyHttpRequestVo = new PyHttpRequestVo();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("code", code);
        paramMap.put("client_id", clientId);
        paramMap.put("client_secret", clientSecret);
        pyHttpRequestVo.setParams(paramMap);
        String tokenResponse = PyHttpClient.doPostByJsonParam(pyHttpRequestVo);
        log.info(tokenResponse);
        mode.addObject("code", code);
        mode.setViewName("welcome");
        return mode;
    }

}
