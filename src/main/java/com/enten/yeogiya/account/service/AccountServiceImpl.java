package com.enten.yeogiya.account.service;


import com.enten.yeogiya.account.entity.Account;
import com.enten.yeogiya.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;


    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 회원의 아이디 입니다."));
    }

    @Override
    public Account save(Account createdAccount) {
        return accountRepository.save(createdAccount);
    }
}
