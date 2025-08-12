package com.enten.yeogiya.account.service.request;


import com.enten.yeogiya.account.entity.LoginType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterAccountRequest {


    private final LoginType loginType;

}
