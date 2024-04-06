package com.mycompany.gestionstock.gestionDeStock.controller.api;

import com.mycompany.gestionstock.gestionDeStock.dto.CommandeFournisseurDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mycompany.gestionstock.gestionDeStock.utils.Constants.APP_ROOT;
import static com.mycompany.gestionstock.gestionDeStock.utils.Constants.FOURNISSEUR_ENDPOINT;

@Api(FOURNISSEUR_ENDPOINT)
public interface CommandeFournisseurApi {

    @PostMapping(FOURNISSEUR_ENDPOINT + "/create")
    CommandeFournisseurDto save(@RequestBody CommandeFournisseurDto dto);

    @GetMapping(FOURNISSEUR_ENDPOINT + "/{id}")
    CommandeFournisseurDto findById(Integer id);

    @GetMapping(FOURNISSEUR_ENDPOINT + "/{code}")
    CommandeFournisseurDto findByCode(@PathVariable("code") String code);

    @GetMapping(FOURNISSEUR_ENDPOINT + "/all")
    List<CommandeFournisseurDto> findAll();

    @DeleteMapping(FOURNISSEUR_ENDPOINT + "/delete/{id}")
    void delete(@PathVariable("id") Integer id);

}
