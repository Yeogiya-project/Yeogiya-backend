package com.enten.yeogiya.account.controller.response;


import com.enten.yeogiya.account.entity.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterAccountResponse {

    private String nickname;
    private RoleType roleType;

    public RegisterAccountResponse(String nickname, RoleType roleType) {
        this.nickname = nickname;
        this.roleType = roleType;
    }
}
