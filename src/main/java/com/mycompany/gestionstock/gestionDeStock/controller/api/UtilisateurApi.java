package com.mycompany.gestionstock.gestionDeStock.controller.api;


import com.mycompany.gestionstock.gestionDeStock.dto.UtilisateurDto;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mycompany.gestionstock.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/utilisateurs")
public interface UtilisateurApi {

    @PostMapping(APP_ROOT + "/utilisateurs/")
    UtilisateurDto save(@RequestBody UtilisateurDto dto);

    @GetMapping(APP_ROOT + "/utilisateurs/{idUtilisateur}")
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

    @GetMapping(APP_ROOT + "/utilisateurs/all")
    List<UtilisateurDto> findAll();

    @DeleteMapping(APP_ROOT + "/utilisateurs/delete/{idUtilisateur}")
    void delete(@PathVariable("idUtilisateur") Integer id);

}
