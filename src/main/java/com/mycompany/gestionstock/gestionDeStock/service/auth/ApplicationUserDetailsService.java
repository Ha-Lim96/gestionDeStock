package com.mycompany.gestionstock.gestionDeStock.service.auth;

import com.mycompany.gestionstock.gestionDeStock.exception.EntityNotFoundException;
import com.mycompany.gestionstock.gestionDeStock.exception.ErrorsCodes;
import com.mycompany.gestionstock.gestionDeStock.model.Utilisateur;
import com.mycompany.gestionstock.gestionDeStock.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository repository;


    // chercher un user par son userName
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = repository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException("Aucun utilisateur contenant l'email fourni", ErrorsCodes.UTILISATEUR_NOT_FOUND)
        );

        return new User(utilisateur.getEmail(), utilisateur.getMoteDePasse(), Collections.emptyList());
    }


}
