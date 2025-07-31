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

    public Account(RoleType roleType, LoginType loginType) {
        this.loginType = loginType;
        this.roleType = roleType;
        this.enabled = true;
    }
}
