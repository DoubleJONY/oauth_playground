package com.example.oauth_playground.request;

public class OAuthRequest {

    String grantType;
    String clientId;
    String redirectUri;
    String clientSecret;
    String code;

    public String getGrantType() {
        return grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getCode() {
        return code;
    }
}
