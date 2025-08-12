package com.enten.yeogiya.account.controller.request;


import com.enten.yeogiya.account.entity.LoginType;
import com.enten.yeogiya.account.entity.RoleType;
import com.enten.yeogiya.account.service.request.RegisterAccountRequest;
import com.enten.yeogiya.account_profile.service.request.RegisterAccountProfileRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterAccountRequestForm {


    private final String email;
    private final String nickname;
    private final LoginType loginType;

    public RegisterAccountRequest toRegisterAccountRequest() {
        return new RegisterAccountRequest(loginType);
    }

    public RegisterAccountProfileRequest toRegisterAccountProfileRequest(){
        return new RegisterAccountProfileRequest(email, nickname);
    }



}
