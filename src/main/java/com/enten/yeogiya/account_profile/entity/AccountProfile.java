package com.enten.yeogiya.account_profile.entity;


import com.enten.yeogiya.account.entity.Account;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class AccountProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id" ,nullable = false)
    private Account account;

    public AccountProfile() {
    }

    public AccountProfile( Account account, String email, String nickname) {
        this.nickname = nickname;
        this.email = email;
        this.account = account;
    }
}
