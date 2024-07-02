package com.dac.api.app.exception;

public class UserNotAdminException extends RuntimeException {
    public UserNotAdminException() {
        super("Usuário não é o administrador.");
    }
}
