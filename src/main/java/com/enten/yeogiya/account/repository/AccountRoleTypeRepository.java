package com.enten.yeogiya.account.repository;


import com.enten.yeogiya.account.entity.AccountRoleType;
import com.enten.yeogiya.account.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRoleTypeRepository extends JpaRepository<AccountRoleType, Long> {
    Optional<AccountRoleType> findByRoleType(RoleType roleType);
}
