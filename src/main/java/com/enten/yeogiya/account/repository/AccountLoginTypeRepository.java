package com.enten.yeogiya.account.repository;

import com.enten.yeogiya.account.entity.AccountLoginType;
import com.enten.yeogiya.account.entity.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountLoginTypeRepository extends JpaRepository<AccountLoginType, Long> {

    Optional<AccountLoginType> findByLoginType(LoginType loginType);

}
