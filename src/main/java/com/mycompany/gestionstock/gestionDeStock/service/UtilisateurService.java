package com.mycompany.gestionstock.gestionDeStock.service;

import com.mycompany.gestionstock.gestionDeStock.dto.UtilisateurDto;

import java.util.List;


public interface UtilisateurService {

    UtilisateurDto save(UtilisateurDto dto);

    UtilisateurDto findById(Integer id);

    List<UtilisateurDto> findAll();

    void delete(Integer id);

}
