package com.enten.yeogiya.account.service;


import com.enten.yeogiya.account.entity.LoginType;
import com.enten.yeogiya.account.entity.RoleType;
import com.enten.yeogiya.account_profile.entity.AccountProfile;

public interface SignupService {

    AccountProfile register(RoleType roleType, LoginType loginType, String email, String nickName);

}
