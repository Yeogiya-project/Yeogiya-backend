package com.enten.yeogiya.account.service;

import com.enten.yeogiya.account.entity.*;
import com.enten.yeogiya.account.exception.DuplicateEmailAndNicknameException;
import com.enten.yeogiya.account.exception.DuplicateEmailException;
import com.enten.yeogiya.account.exception.DuplicateNicknameException;
import com.enten.yeogiya.account.repository.AccountLoginTypeRepository;
import com.enten.yeogiya.account.repository.AccountRoleTypeRepository;
import com.enten.yeogiya.account_profile.entity.AccountProfile;
import com.enten.yeogiya.account_profile.service.AccountProfileService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private SignupServiceImpl signupService;

    @Mock
    private AccountProfileService accountProfileService;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountRoleTypeRepository accountRoleTypeRepository;

    @Mock
    private AccountLoginTypeRepository accountLoginTypeRepository;



    @Test
    void 회원가입_성공(){
        // given
        String email = "abc123@naver.com";
        String nickName = "짱짱맨";

        Account account = new Account(RoleType.USER, LoginType.KAKAO);
        AccountProfile accountProfile = new AccountProfile(account, nickName, email);

        when(accountProfileService.existByEmail(email)).thenReturn(false);
        when(accountProfileService.existByNickname(nickName)).thenReturn(false);
        when(accountRoleTypeRepository.findByRoleType(RoleType.USER))
                .thenReturn(Optional.of(new AccountRoleType(RoleType.USER)));
        when(accountLoginTypeRepository.findByLoginType(LoginType.KAKAO))
                .thenReturn(Optional.of(new AccountLoginType(LoginType.KAKAO)));
        when(accountService.save(any(Account.class))).thenReturn(account);
        when(accountProfileService.save(any(AccountProfile.class))).thenReturn(accountProfile);

        // when
        AccountProfile result = signupService.register(RoleType.USER, LoginType.KAKAO, email, nickName);

        // then
        Assertions.assertThat(result).isEqualTo(accountProfile);
    }

    @Test
    void 이메일_중복이면_회원가입_실패() {
        // given
        String email = "abc123@naver.com";
        String nickName = "짱짱맨";

        when(accountProfileService.existByEmail(email)).thenReturn(true);
        when(accountProfileService.existByNickname(nickName)).thenReturn(false);

        // when, then
        assertThrows(DuplicateEmailException.class, () -> {
            signupService.register(RoleType.USER, LoginType.KAKAO, email, nickName);
        });
    }


    @Test
    void 닉네임_중복이면_회원가입_실패() {
        // given
        String email = "abc123@naver.com";
        String nickName = "짱짱맨";

        when(accountProfileService.existByEmail(email)).thenReturn(false);
        when(accountProfileService.existByNickname(nickName)).thenReturn(true);

        // when, then
        assertThrows(DuplicateNicknameException.class, () -> {
            signupService.register(RoleType.USER, LoginType.KAKAO, email, nickName);
        });
    }

    @Test
    void 이메일_닉네임_모두_중복이면_회원가입_실패(){
        // given
        String email = "abc123@naver.com";
        String nickName = "짱짱맨";

        when(accountProfileService.existByEmail(email)).thenReturn(true);
        when(accountProfileService.existByNickname(nickName)).thenReturn(true);

        // when, then
        assertThrows(DuplicateEmailAndNicknameException.class, () -> {
            signupService.register(RoleType.USER, LoginType.KAKAO, email, nickName);
        });
    }




}
