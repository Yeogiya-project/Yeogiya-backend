package com.enten.yeogiya.account_profile.service.request;


import com.enten.yeogiya.account.entity.LoginType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterAccountProfileRequest {

    private final String nickname;
    private final String email;

}
