package com.enten.yeogiya.kakao_authentication.service;


import java.util.Map;

public interface KakaoAuthenticationService {

    String getLoginLink();
    Map<String, Object> getAccessToken(String code);
    Map<String, Object> getUserInfo(String accessToken);

}
