package com.mycompany.gestionstock.gestionDeStock.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

    // data envoyé comme réponse
    private String accessToken;


}
