package com.enten.yeogiya.kakao_authentication.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KakaoAuthenticationServiceTest {

    private KakaoAuthenticationServiceImpl kakaoAuthenticationService;

    @Mock
    private RestTemplate restTemplate;

    private final String TEST_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private final String TEST_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    @BeforeEach
    void setUp() {
        kakaoAuthenticationService = new KakaoAuthenticationServiceImpl(
                "https://kauth.kakao.com/",
                "test_client_id",
                "test_redirect_uri",
                TEST_TOKEN_URL,
                TEST_USER_INFO_URL,
                restTemplate
        );
    }

    @Test
    void 유효한_코드일때_AccessToken을_반환합니다() {
        // given
        String testCode = "test_code";
        String expectedToken = "test_access_token";

        Map<String, Object> tokenResponse = new HashMap<>();
        tokenResponse.put("access_token", expectedToken);
        ResponseEntity<Map> mockResponseEntity = new ResponseEntity<>(tokenResponse, HttpStatus.OK);

        // eq() 매처를 사용하여 어떤 URL과 HttpMethod로 호출될지 명확하게 지정
        when(restTemplate.exchange(
                eq(TEST_TOKEN_URL),
                eq(HttpMethod.POST),
                any(HttpEntity.class), // HttpEntity는 내용이 복잡하므로 any()를 사용하거나 별도 검증
                eq(Map.class)
        )).thenReturn(mockResponseEntity);

        // when
        Map<String, Object> actualResponse = kakaoAuthenticationService.getAccessToken(testCode);

        // then
        // 실제 반환값(actualResponse)과 기대값(expectedToken)을 검증
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(expectedToken, actualResponse.get("access_token"));
    }

    @Test
    void 유효한_토큰일_때_유저_정보를_반환합니다() {
        // given
        String testAccessToken = "test_access_token";
        String expectedEmail = "test@example.com";
        String expectedNickname = "test123";

        Map<String, Object> userInfoResponse = new HashMap<>();
        userInfoResponse.put("kakao_account", Map.of("email", expectedEmail, "nickname", expectedNickname));
        ResponseEntity<Map> mockResponseEntity = new ResponseEntity<>(userInfoResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(TEST_USER_INFO_URL),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Map.class)
        )).thenReturn(mockResponseEntity);

        // when
        Map<String, Object> actualUserInfo = kakaoAuthenticationService.getUserInfo(testAccessToken);

        // then
        Assertions.assertNotNull(actualUserInfo);
        Map<String, Object> kakaoAccount = (Map<String, Object>) actualUserInfo.get("kakao_account");
        Assertions.assertEquals(expectedEmail, kakaoAccount.get("email"));
        Assertions.assertEquals(expectedNickname, kakaoAccount.get("nickname"));
    }




    @Test
    void 사용자가_신규_사용자인_경우(){



    }

    @Test
    void 사용자가_기존_사용자인_경우(){

    }



}
