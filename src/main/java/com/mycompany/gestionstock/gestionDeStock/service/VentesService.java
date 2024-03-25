package com.mycompany.gestionstock.gestionDeStock.service;

import com.mycompany.gestionstock.gestionDeStock.dto.VentesDto;

import java.util.List;

public interface VentesService {

    VentesDto save(VentesDto dto);

    VentesDto findById(Integer id);

    VentesDto findVenteByCode(String code);

    List<VentesDto> findAll();

    void delete(Integer id);

}
