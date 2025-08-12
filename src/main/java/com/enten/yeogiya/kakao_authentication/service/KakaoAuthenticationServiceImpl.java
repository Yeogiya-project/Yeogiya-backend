package com.enten.yeogiya.kakao_authentication.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
