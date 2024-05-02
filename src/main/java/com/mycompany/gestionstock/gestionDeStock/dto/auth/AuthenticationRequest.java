package com.mycompany.gestionstock.gestionDeStock.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {

    // les crédentials
    private String login;

    private String password;
}
