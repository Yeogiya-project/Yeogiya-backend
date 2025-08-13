package com.enten.yeogiya.kakao_authentication.service;


import com.enten.yeogiya.kakao_authentication.service.response.KakaoLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class KakaoAuthenticationServiceImpl implements KakaoAuthenticationService {

    private final String loginUrl;
    private final String clientId;
    private final String redirectUri;
    private final String tokenRequestUri;
    private final String userInfoRequestUri;

    private final RestTemplate restTemplate;


    public KakaoAuthenticationServiceImpl(
            @Value("${kakao.login-url}") String loginUrl,
            @Value("${kakao.client-id}") String clientId,
            @Value("${kakao.redirect-uri}") String redirectUri,
            @Value("${kakao.token-request-uri}") String tokenRequestUri,
            @Value("${kakao.user-info-request-uri}") String userInfoRequestUri,
            RestTemplate restTemplate
    ) {
        this.loginUrl = loginUrl;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.tokenRequestUri = tokenRequestUri;
        this.userInfoRequestUri = userInfoRequestUri;

        this.restTemplate = restTemplate;
    }


    @Override
    public String getLoginLink() {
        log.info("getLoginLink메소드 called");

        return String.format("%s/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
                loginUrl, clientId, redirectUri);
    }

    @Override
    public Map<String, Object> getAccessToken(String code) {

        // MultiValueMap <- Key, Value를 여러 다발로 받기 위해 사용
        // Kakao 벤더에서 요구하는 데이터셋 구성
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token <- 형식
        // 위 링크에서 본문(Body)에 해당하는 내용을 맞춰 보내도록 구성하는 작업
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);
        formData.add("client_secret", "");

        // 헤더 구성
        // 위 링크의 `요청 헤더`에 해당하는 파트
        // Content-Type: application/x-www-form-urlencoded 로 구성
        // 이것은 Spring에서 `MediaType.APPLICATION_FORM_URLENCODED` 로 사용
        // Post 방식으로 전송하겠다는 의미 내포
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(formData, httpHeaders);

        ResponseEntity<Map> response = restTemplate.exchange(
                tokenRequestUri, HttpMethod.POST, httpEntity, Map.class
        );

        return response.getBody();

    }

    @Override
    public Map<String, Object> getUserInfo(String accessToken) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<Map> response = restTemplate.exchange(
                userInfoRequestUri, HttpMethod.GET, entity, Map.class
        );


        log.info("User Info : {} ", response.getBody());

        return response.getBody();
    }




}
