package com.enten.yeogiya.account.service;

import com.enten.yeogiya.account.entity.Account;

public interface AccountService {

    Account findById(Long id);
    Account save(Account createdAccount);
}
