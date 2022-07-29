package com.example.oauth_playground.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Controller
public class NaverController {

    @RequestMapping(value = "/login/success_naver")
    public @ResponseBody
    String getNaverSuccess(
            HttpServletRequest request) throws Exception {

        return "200";
    }

    @RequestMapping(value = "/login/get_naver_auth_url")
    public @ResponseBody
    String getNaverAuthUrl(
            HttpServletRequest request) throws Exception {
        String reqUrl =
                "https://nid.naver.com/oauth2.0/authorize"
                        + "?client_id=jdGPLeEkwN4DbXVmxYi7"
                        + "&redirect_uri=http://localhost:8080/login/oauth_naver"
                        + "&response_type=code";

        return reqUrl;
    }

    // 카카오 연동정보 조회
    @RequestMapping(value = "/login/oauth_naver")
    public String oauthNaver(
            @RequestParam(value = "code", required = false) String code
            , Model model) throws Exception {

        String uri = "https://nid.naver.com/oauth2.0/token"
                + "?grant_type=authorization_code"
                + "&client_id=jdGPLeEkwN4DbXVmxYi7"
                + "&client_secret=TOmDkmKCyT"
                + "&code=" + code
                + "&state=";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri, String.class);
        System.out.println("###response#### : " + response);
        return response;
    }
}
