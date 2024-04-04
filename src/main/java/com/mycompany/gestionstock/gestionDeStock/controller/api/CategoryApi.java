package com.mycompany.gestionstock.gestionDeStock.controller.api;

import com.mycompany.gestionstock.gestionDeStock.dto.CategoryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mycompany.gestionstock.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/categories")
public interface CategoryApi {


    @PostMapping(value = APP_ROOT + "/categories/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une categorie (Ajouter / Modifier)", notes = "Cette méthode permet d'enrgistrer ou modifier une categorie", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet 'Category' Créé / Modifié avec succès"),
            @ApiResponse(code = 400, message = "L'objet 'Category' Créé / Modifié avec succès")
    })
    CategoryDto save(@RequestBody CategoryDto dto);


    @GetMapping(value= APP_ROOT + "/categories/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "consulter une categorie", notes = "Cette méthode permet de consulter une categorie utilisant son ID", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet a été trouvé dans la base de données"),
            @ApiResponse(code = 404, message = "Aucune categorie existe dans la base de données avec l'ID fourni")
    })
    CategoryDto findById(@PathVariable("idCategory") Integer id);


    @GetMapping(value= APP_ROOT + "/categories/{codeCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "consulter un article", notes = "Cette méthode permet de consulter une categorie utilisant son code", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet a été trouvé dans la base de données"),
            @ApiResponse(code = 404, message = "Aucune categorie existe dans la base de données avec le CODE fourni")
    })
    CategoryDto findByCode(@PathVariable("codeCategory") String code);

    @GetMapping(value= APP_ROOT + "/categories/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Affiche la liste des categories", notes = "Cette méthode permet de de d'afficher la liste des categories existants dans la base de données", responseContainer = "List<CategoryDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des categories / Une liste vide"),
    })
    List<CategoryDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/categories/delete/{idCategory}")
    @ApiOperation(value = "Supprimer une categorie", notes = "Cette méthode permet la suppression d'une categorie utilisant son ID",responseContainer = "Void")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La categorie a été supprimé"),
    })
    void delete(@PathVariable("idCategory") Integer id);


}
