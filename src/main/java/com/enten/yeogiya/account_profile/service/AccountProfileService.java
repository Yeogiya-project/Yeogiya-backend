package com.enten.yeogiya.account_profile.service;

import com.enten.yeogiya.account_profile.entity.AccountProfile;

public interface AccountProfileService {

    boolean existByEmail(String email);
    boolean existByNickname(String nickname);


    AccountProfile save(AccountProfile createdAccountProfile);
}
