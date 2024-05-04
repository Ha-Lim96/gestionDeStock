package com.mycompany.gestionstock.gestionDeStock.controller;

import com.mycompany.gestionstock.gestionDeStock.dto.auth.AuthenticationRequest;
import com.mycompany.gestionstock.gestionDeStock.dto.auth.AuthenticationResponse;
import com.mycompany.gestionstock.gestionDeStock.model.auth.ExtendedUser;
import com.mycompany.gestionstock.gestionDeStock.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mycompany.gestionstock.gestionDeStock.utils.Constants.APP_ROOT;

@RestController
@RequestMapping(APP_ROOT + "/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;



    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        // vérifie l'existance des credentials dans BDD
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        // charger l'utilisateur
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());

        // génération du Token
        final String jwt = jwtUtil.generateToken((ExtendedUser) userDetails);

        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());
    }

}
