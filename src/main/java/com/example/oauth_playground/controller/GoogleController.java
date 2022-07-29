package com.example.oauth_playground.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

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
                        + "&scope=https://www.googleapis.com/auth/userinfo.profile";

        return reqUrl;
    }

    // 카카오 연동정보 조회
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

        String response = restTemplate.postForObject(uri, googleToken, String.class);
        System.out.println("###response#### : " + response);
        return response;
    }

    public static class GoogleToken {
        public String grant_type;
        public String client_id;
        public String client_secret;
        public String code;
        public String redirect_uri;

        public String getGrant_type() {

            return grant_type;
        }

        public void setGrant_type(String grant_type) {

            this.grant_type = grant_type;
        }

        public String getClient_id() {

            return client_id;
        }

        public void setClient_id(String client_id) {

            this.client_id = client_id;
        }

        public String getClient_secret() {

            return client_secret;
        }

        public void setClient_secret(String client_secret) {

            this.client_secret = client_secret;
        }

        public String getCode() {

            return code;
        }

        public void setCode(String code) {

            this.code = code;
        }

        public String getRedirect_uri() {

            return redirect_uri;
        }

        public void setRedirect_uri(String redirect_uri) {

            this.redirect_uri = redirect_uri;
        }
    }
}
