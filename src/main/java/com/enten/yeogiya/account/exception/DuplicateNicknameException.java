package com.enten.yeogiya.account.exception;

public class DuplicateNicknameException extends RuntimeException{


    public DuplicateNicknameException(String message) {
        super(message);
    }

    public DuplicateNicknameException() {
        super("닉네임 중복 입니다.");
    }
}
