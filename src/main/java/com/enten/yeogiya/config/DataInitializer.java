package com.enten.yeogiya.config;

import com.enten.yeogiya.account.entity.AccountLoginType;
import com.enten.yeogiya.account.entity.AccountRoleType;
import com.enten.yeogiya.account.entity.LoginType;
import com.enten.yeogiya.account.entity.RoleType;
import com.enten.yeogiya.account.repository.AccountLoginTypeRepository;
import com.enten.yeogiya.account.repository.AccountRoleTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final AccountRoleTypeRepository accountRoleTypeRepository;
    private final AccountLoginTypeRepository accountLoginTypeRepository;

    @PostConstruct
    public void init() {
        // RoleType 초기화
        if (accountRoleTypeRepository.count() == 0) {
            accountRoleTypeRepository.save(new AccountRoleType(RoleType.ADMIN));
            accountRoleTypeRepository.save(new AccountRoleType(RoleType.USER));
        }

        // LoginType 초기화
        if (accountLoginTypeRepository.count() == 0) {
            accountLoginTypeRepository.save(new AccountLoginType(LoginType.KAKAO));
            accountLoginTypeRepository.save(new AccountLoginType(LoginType.GOOGLE));
            accountLoginTypeRepository.save(new AccountLoginType(LoginType.NAVER));
        }
    }
}
