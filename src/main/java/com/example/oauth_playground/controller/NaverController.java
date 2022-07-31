package com.example.oauth_playground.controller;

import com.example.oauth_playground.response.OAuthResponse;
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
import java.util.Objects;

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
        ResponseEntity<OAuthResponse> response = restTemplate.getForEntity(uri, OAuthResponse.class);

        if(!response.getStatusCode().is2xxSuccessful()) {;
            return Objects.requireNonNull(response.getBody()).toString();
        }

        Object userInfo = getUserInfo(response.getBody().getAccessToken());

        System.out.println("###userInfo#### : " + userInfo.toString());
        return userInfo.toString();
    }

    private Object getUserInfo(String accessToken) {

        String uri = "https://openapi.naver.com/v1/nid/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity request = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        return response.getBody();
    }
}
