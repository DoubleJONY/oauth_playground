package com.example.oauth_playground.controller;

import com.example.oauth_playground.response.OAuthResponse;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Objects;

@Controller
public class KakaoController {

    @RequestMapping(value = "/login/kakao_success")
    public @ResponseBody
    String getKakaoSuccess(
            HttpServletRequest request) throws Exception {

        return "200";
    }

    @RequestMapping(value = "/login/get_kakao_auth_url")
    public @ResponseBody
    String getKakaoAuthUrl(
            HttpServletRequest request) throws Exception {
        String reqUrl =
                "https://kauth.kakao.com/oauth/authorize"
                        + "?client_id=90dbe4eff66b552da7c01d93dd46e11e"
                        + "&redirect_uri=http://localhost:8080/login/oauth_kakao"
                        + "&response_type=code";

        return reqUrl;
    }

    @RequestMapping(value = "/login/oauth_kakao")
    public String oauthKakao(
            @RequestParam(value = "code", required = false) String code
            , Model model) throws Exception {

        String uri = "https://kauth.kakao.com/oauth/token"
                + "?grant_type=authorization_code"
                + "&client_id=90dbe4eff66b552da7c01d93dd46e11e"
                + "&redirect_uri=http://localhost:8080/login/oauth_kakao"
                + "&client_secret=TOmDkmKCyT"
                + "&code=" + code;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OAuthResponse> response = restTemplate.getForEntity(uri, OAuthResponse.class);

        if(!response.getStatusCode().is2xxSuccessful()) {;
            return Objects.requireNonNull(response.getBody()).toString();
        }

        Object userInfo = getUserInfo(response.getBody().getAccessToken());

        System.out.println("###userInfo#### : " + userInfo.toString());
        return userInfo.toString();

    }

    private Object getUserInfo(String accessToken) {

        String uri = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity request = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        return response.getBody();
    }
}
