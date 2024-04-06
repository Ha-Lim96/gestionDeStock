package com.mycompany.gestionstock.gestionDeStock.controller.api;

import com.mycompany.gestionstock.gestionDeStock.dto.EntrepriseDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mycompany.gestionstock.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/entreprises")
public interface EntrepriseApi {

    @PostMapping(APP_ROOT + "/entreprises/create")
    EntrepriseDto save(@RequestBody EntrepriseDto dto);


    @GetMapping(APP_ROOT + "/entreprises/{idEntreprise}")
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);


    @GetMapping(APP_ROOT + "/entreprises/all")
    List<EntrepriseDto> findAll();


    @DeleteMapping(APP_ROOT + "/delete/{idEntreprise}")
    void delete(@PathVariable("idEntreprise") Integer id);
}
