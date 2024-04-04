package com.mycompany.gestionstock.gestionDeStock.controller.api;

import com.mycompany.gestionstock.gestionDeStock.dto.CommandeClientDto;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mycompany.gestionstock.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/commandeClients")
public interface CommandeClientApi {

    @PostMapping(value = APP_ROOT + "/commandeclients/create")
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);

    @GetMapping(value = APP_ROOT + "/commandeclients/{idCommandeClient}")
    ResponseEntity<CommandeClientDto> findById(@PathVariable("idCommandeClient") Integer id);

    @GetMapping(value = APP_ROOT + "/commandeclients/{codeCommandeClient}")
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCommandeClient") String code);

    @GetMapping(value = APP_ROOT + "/commandeclients/all")
    ResponseEntity<List<CommandeClientDto>> findAll();

    @DeleteMapping(value = APP_ROOT + "/commandeclients/delete/{id}")
    ResponseEntity delete(Integer id);

}
