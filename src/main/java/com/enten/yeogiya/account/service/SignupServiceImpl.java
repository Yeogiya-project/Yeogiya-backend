package com.enten.yeogiya.account.service;


import com.enten.yeogiya.account.entity.*;
import com.enten.yeogiya.account.exception.DuplicateEmailAndNicknameException;
import com.enten.yeogiya.account.exception.DuplicateEmailException;
import com.enten.yeogiya.account.exception.DuplicateNicknameException;
import com.enten.yeogiya.account.repository.AccountLoginTypeRepository;
import com.enten.yeogiya.account.repository.AccountRoleTypeRepository;
import com.enten.yeogiya.account_profile.entity.AccountProfile;
import com.enten.yeogiya.account_profile.service.AccountProfileService;
import com.enten.yeogiya.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {

    private final AccountProfileService accountProfileService;
    private final AccountService accountService;
    private final AccountRoleTypeRepository accountRoleTypeRepository;
    private final AccountLoginTypeRepository accountLoginTypeRepository;

    @Override
    public AccountProfile register( LoginType loginType, String email, String nickName) {

        boolean emailExists = accountProfileService.existByEmail(email);
        boolean nicknameExists = accountProfileService.existByNickname(nickName);

        if (emailExists && nicknameExists) {
            throw new DuplicateEmailAndNicknameException();
        }

        if (emailExists) {
            throw new DuplicateEmailException();
        }

        if (nicknameExists) {
            throw new DuplicateNicknameException();
        }

        AccountRoleType accountRoleType = accountRoleTypeRepository.findByRoleType(RoleType.USER)
                .orElseThrow(() -> new IllegalStateException("RoleType.USER 가 DB에 없습니다."));

        AccountLoginType accountLoginType = accountLoginTypeRepository.findByLoginType(loginType)
                .orElseThrow(() -> new IllegalStateException("LoginType.%s 이 DB에 없습니다.".formatted(loginType)));

        Account createdAccount = new Account(loginType);
        Account savedAccount = accountService.save(createdAccount);


        AccountProfile createdAccountProfile = new AccountProfile(savedAccount, nickName, email);
        AccountProfile savedAccountProfile = accountProfileService.save(createdAccountProfile);

        return savedAccountProfile;
    }
}
