package com.enten.yeogiya.account_profile.service;


import com.enten.yeogiya.account_profile.entity.AccountProfile;
import com.enten.yeogiya.account_profile.repository.AccountProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountProfileServiceImpl implements AccountProfileService {

    private final AccountProfileRepository accountProfileRepository;


    @Override
    public boolean existByEmail(String email) {
        return accountProfileRepository.existsByEmail(email);
    }

    @Override
    public boolean existByNickname(String nickname) {
        return accountProfileRepository.existsByNickname(nickname);
    }

    @Override
    public AccountProfile save(AccountProfile createdAccountProfile) {
        return accountProfileRepository.save(createdAccountProfile);
    }

}
