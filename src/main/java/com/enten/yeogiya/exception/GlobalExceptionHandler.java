package com.enten.yeogiya.exception;

import com.enten.yeogiya.account.exception.DuplicateEmailAndNicknameException;
import com.enten.yeogiya.account.exception.DuplicateEmailException;
import com.enten.yeogiya.account.exception.DuplicateNicknameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailAndNicknameException.class)
    public ResponseEntity<?> handleDuplicateEmailAndNicknameException() {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "errorCode", "DUPLICATE_EMAIL_AND_NICKNAME",
                        "message", "이메일과 닉네임이 모두 중복되었습니다."
                ));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<?> handleDuplicateEmailException() {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "errorCode", "DUPLICATE_EMAIL",
                        "message", "이메일이 중복되었습니다."
                ));
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<?> handleDuplicateNicknameException() {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "errorCode", "DUPLICATE_NICKNAME",
                        "message", "닉네임이 중복되었습니다."
                ));
    }
}
