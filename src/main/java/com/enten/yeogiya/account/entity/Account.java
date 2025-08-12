package com.enten.yeogiya.account.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    private Boolean enabled;

    public Account(LoginType loginType) {
        this.loginType = loginType;
        this.roleType = RoleType.USER;
        this.enabled = true;
    }
}
