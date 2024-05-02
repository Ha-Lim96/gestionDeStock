package com.mycompany.gestionstock.gestionDeStock.config;

import com.mycompany.gestionstock.gestionDeStock.service.auth.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;

    // pour que spring sache qu'est ce qu'il doit faire pour récupérer / authentificer cet utilisateur
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // On a précisé le service à utiliser pour chercher les informations de l'utilisateur
        auth.userDetailsService(applicationUserDetailsService);
    }

    // demander à spring bloquer toute requettes différentes de celle qui a été mentionnée
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/**/authenticate").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

}
