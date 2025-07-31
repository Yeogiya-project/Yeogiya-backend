package com.enten.yeogiya.account.controller.request;


import com.enten.yeogiya.account.entity.LoginType;
import com.enten.yeogiya.account.entity.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterAccountRequest {


    private final String email;
    private final String nickname;
    private final LoginType loginType;
    private final RoleType roleType;



}
