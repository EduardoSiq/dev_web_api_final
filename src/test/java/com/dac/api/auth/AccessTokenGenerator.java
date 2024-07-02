package com.dac.api.auth;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AccessTokenGenerator {
    public static void main(final String[] args) {
        // Credenciais
        final String userCredentials = "user:password";
        final String adminCredentials = "admin:admin";

        // Codificar em Base64
        final String userAuth = Base64.getEncoder().encodeToString(userCredentials.getBytes());
        final String adminAuth = Base64.getEncoder().encodeToString(adminCredentials.getBytes());

        // Criar um mapa para armazenar as credenciais
        final Map<String, String> authMap = new HashMap<>();
        authMap.put("user_auth", userAuth);
        authMap.put("admin_auth", adminAuth);
        
        System.out.println(authMap.entrySet());
    }

}
