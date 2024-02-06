package com.energizor.restapi.exception;

public class DuplicatedMemberEmailException extends RuntimeException {
    public DuplicatedMemberEmailException(String message) {
        super(message);
    }
}
