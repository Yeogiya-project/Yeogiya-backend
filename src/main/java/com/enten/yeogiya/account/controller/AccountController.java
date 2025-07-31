package com.enten.yeogiya.account.controller;


import com.enten.yeogiya.account.controller.request.RegisterAccountRequest;
import com.enten.yeogiya.account.controller.response.RegisterAccountResponse;
import com.enten.yeogiya.account.service.AccountService;
import com.enten.yeogiya.account.service.SignupService;
import com.enten.yeogiya.account_profile.entity.AccountProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final SignupService signupService;

    @PostMapping("/register")
    public ResponseEntity<RegisterAccountResponse> register(@RequestBody RegisterAccountRequest request) {
        log.info("회원 가입 요청 request = {}", request);


        AccountProfile registeredAccountProfile = signupService.register(
                request.getRoleType(), request.getLoginType(), request.getEmail(), request.getNickname()
        );

        if (registeredAccountProfile == null) {
            return ResponseEntity.badRequest().build();
        }

        RegisterAccountResponse registerAccountResponse = new RegisterAccountResponse(
                registeredAccountProfile.getNickname(),
                registeredAccountProfile.getAccount().getRoleType()
        );


        return ResponseEntity.ok(registerAccountResponse);



    }


}
