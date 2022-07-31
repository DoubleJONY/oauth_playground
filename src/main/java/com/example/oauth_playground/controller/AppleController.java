package com.example.oauth_playground.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AppleController {

    @RequestMapping(value = "/login/apple_success")
    public @ResponseBody
    String getAppleSuccess(
            HttpServletRequest request) throws Exception {

        return "200";
    }

    @RequestMapping(value = "/login/get_apple_auth_url")
    public @ResponseBody
    String getAppleAuthUrl(
            HttpServletRequest request) throws Exception {
        String reqUrl =
                "https://kauth.kakao.com/oauth/authorize"
                        + "?client_id=90dbe4eff66b552da7c01d93dd46e11e"
                        + "&redirect_uri=http://localhost:8080/login/oauth_kakao"
                        + "&response_type=code";

        return reqUrl;
    }

    @RequestMapping(value = "/login/oauth_apple")
    public String oauthApple(
            @RequestParam(value = "code", required = false) String code
            , Model model) throws Exception {

        String uri = "https://kauth.kakao.com/oauth/token"
                + "?grant_type=authorization_code"
                + "&client_id=90dbe4eff66b552da7c01d93dd46e11e"
                + "&redirect_uri=http://localhost:8080/login/oauth_kakao"
                + "&client_secret=TOmDkmKCyT"
                + "&code=" + code;

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri, String.class);
        System.out.println("###response#### : " + response);
        return response;
    }
}
