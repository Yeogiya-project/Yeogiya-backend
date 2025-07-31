package com.enten.yeogiya.account.exception;

public class DuplicateEmailAndNicknameException extends RuntimeException{


    public DuplicateEmailAndNicknameException(String message) {
        super(message);
    }

    public DuplicateEmailAndNicknameException() {
        super("이메일과 닉네임 모두 중복 입니다.");
    }
}
