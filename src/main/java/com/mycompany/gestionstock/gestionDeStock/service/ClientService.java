package com.mycompany.gestionstock.gestionDeStock.service;

import com.mycompany.gestionstock.gestionDeStock.dto.ClientDto;

import java.util.List;



public interface ClientService {


    ClientDto save(ClientDto dto);

    ClientDto findById(Integer id);

    List<ClientDto> findAll();

    void delete(Integer id);

}
