package com.mycompany.gestionstock.gestionDeStock.controller.api;

import com.mycompany.gestionstock.gestionDeStock.dto.FournisseurDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mycompany.gestionstock.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/fournisseurs")
public interface FournisseurApi {

    @PostMapping(APP_ROOT + "/fournisseurs/create")
    FournisseurDto save(@RequestBody FournisseurDto dto);

    @GetMapping(APP_ROOT + "/fournisseurs/{idFournisseur}")
    FournisseurDto findById(Integer id);

    @GetMapping(APP_ROOT + "/fournisseurs")
    List<FournisseurDto> findAll();

    @DeleteMapping(APP_ROOT + "/delete/{idFournisseur}")
    void delete(@PathVariable("idFournisseur") Integer id);
}
