package com.enten.yeogiya.kakao_authentication.controller.request_form;

import com.enten.yeogiya.account.entity.LoginType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KakaoLoginResponseForm {

    private final boolean newUser;
    private final String email;
    private final String nickname;
    private final String token;
    private final LoginType loginType;





}
