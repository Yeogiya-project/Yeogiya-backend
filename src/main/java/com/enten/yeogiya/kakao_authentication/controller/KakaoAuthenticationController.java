package com.enten.yeogiya.kakao_authentication.controller;


import com.enten.yeogiya.kakao_authentication.service.KakaoAuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao-authentication")
public class KakaoAuthenticationController {

    private final KakaoAuthenticationService kakaoAuthenticationService;

    @GetMapping("/request-login-url")
    public String requestLoginUrl(){
        log.info("request -> getLoginUrl called");

        return kakaoAuthenticationService.getLoginLink();
    }







}
