package com.example.oauth_playground.controller;

import com.example.oauth_playground.request.GoogleToken;
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
public class GoogleController {

    @RequestMapping(value = "/login/google_success")
    public @ResponseBody
    String getGoogleSuccess(
            HttpServletRequest request) throws Exception {

        return "200";
    }

    @RequestMapping(value = "/login/get_google_auth_url")
    public @ResponseBody
    String getGoogleAuthUrl(
            HttpServletRequest request) throws Exception {
        String reqUrl =
                "https://accounts.google.com/o/oauth2/auth"
                        + "?client_id=143550101505-m4fi6u9v0r3e7spjk231ftl67dq2aprg.apps.googleusercontent.com"
                        + "&redirect_uri=http://localhost:8080/login/oauth_google"
                        + "&response_type=code"
                        + "&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";

        return reqUrl;
    }

    @RequestMapping(value = "/login/oauth_google")
    public String oauthGoogle(
            @RequestParam(value = "code", required = false) String code
            , Model model) throws Exception {

        String uri = "https://oauth2.googleapis.com/token";

        RestTemplate restTemplate = new RestTemplate();

        GoogleToken googleToken = new GoogleToken();
        googleToken.setGrant_type("authorization_code");
        googleToken.setClient_id("143550101505-m4fi6u9v0r3e7spjk231ftl67dq2aprg.apps.googleusercontent.com");
        googleToken.setClient_secret("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");
        googleToken.setCode(code);
        googleToken.setRedirect_uri("http://localhost:8080/login/oauth_google");

        ResponseEntity<OAuthResponse> response = restTemplate.postForEntity(uri, googleToken, OAuthResponse.class);

        if(!response.getStatusCode().is2xxSuccessful()) {;
            return Objects.requireNonNull(response.getBody()).toString();
        }

        Object userInfo = getUserInfo(response.getBody().getAccessToken());

        System.out.println("###userInfo#### : " + userInfo.toString());
        return userInfo.toString();
    }

    private Object getUserInfo(String accessToken) {

        String uri = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity request = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        return response.getBody();
    }
}
