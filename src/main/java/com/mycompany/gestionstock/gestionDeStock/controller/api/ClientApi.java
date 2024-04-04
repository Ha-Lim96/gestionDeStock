package com.mycompany.gestionstock.gestionDeStock.controller.api;

import com.mycompany.gestionstock.gestionDeStock.dto.CategoryDto;
import com.mycompany.gestionstock.gestionDeStock.dto.ClientDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mycompany.gestionstock.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/clients")
public interface ClientApi {

    @PostMapping(value = APP_ROOT + "/clients/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un client (Ajouter / Modifier)", notes = "Cette méthode permet d'enrgistrer ou modifier un client", response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet 'Client' Créé / Modifié avec succès"),
            @ApiResponse(code = 400, message = "L'objet 'Client' Créé / Modifié avec succès")
    })
    ClientDto save(@RequestBody ClientDto dto);


    @GetMapping(value= APP_ROOT + "/clients/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "consulter un client", notes = "Cette méthode permet de consulter un client utilisant son ID", response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet a été trouvé dans la base de données"),
            @ApiResponse(code = 404, message = "Aucun client existe dans la base de données avec l'ID fourni")
    })
    ClientDto findById(@PathVariable("idClient") Integer id);


    @GetMapping(value= APP_ROOT + "/clients/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Affiche la liste des clients", notes = "Cette méthode permet de de d'afficher la liste des clients existants dans la base de données", responseContainer = "List<ClientDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des clients / Une liste vide"),
    })
    List<ClientDto> findAll();


    @DeleteMapping(value = APP_ROOT + "/clients/delete/{idClient}")
    @ApiOperation(value = "Supprimer un client", notes = "Cette méthode permet la suppression d'un client utilisant son ID",responseContainer = "Void")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client a été supprimé"),
    })
    void delete(@PathVariable("idClient") Integer id);

}
