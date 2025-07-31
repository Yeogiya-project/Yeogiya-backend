package com.enten.yeogiya.account_profile.repository;

import com.enten.yeogiya.account_profile.entity.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountProfileRepository extends JpaRepository<AccountProfile,Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
