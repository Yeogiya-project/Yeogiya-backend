package com.enten.yeogiya.account.repository;

import com.enten.yeogiya.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
