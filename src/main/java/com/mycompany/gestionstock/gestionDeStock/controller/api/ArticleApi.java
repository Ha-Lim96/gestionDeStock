package com.mycompany.gestionstock.gestionDeStock.controller.api;

import com.mycompany.gestionstock.gestionDeStock.dto.ArticleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mycompany.gestionstock.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/articles")
public interface ArticleApi {


    @PostMapping(value = APP_ROOT + "/articles/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un article (Ajouter / Modifier)", notes = "Cette méthode permet d'enrgistrer ou modifier un article", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet Article Créé / Modifié avec succès"),
            @ApiResponse(code = 400, message = "L'objet Article Créé / Modifié avec succès")
    })
    ArticleDto save(@RequestBody ArticleDto dto);


    @GetMapping(value= APP_ROOT + "/articles/{idArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "consulter un article", notes = "Cette méthode permet de consulter un article utilisant son ID", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet a été trouvé dans la base de données"),
            @ApiResponse(code = 404, message = "Aucun article existe dans la base de données avec l'ID fourni")
    })
    ArticleDto findById(@PathVariable("idArticle") Integer id);


    @GetMapping(value= APP_ROOT + "/articles/{codeArticle}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "consulter un article", notes = "Cette méthode permet de consulter un article utilisant son codeArticle", response = ArticleDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'objet a été trouvé dans la base de données"),
            @ApiResponse(code = 404, message = "Aucun article existe dans la base de données avec le CODE fourni")
    })
    ArticleDto findByCodeArticle(@PathVariable String codeArticle);


    @GetMapping(value= APP_ROOT + "/articles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Affiche la liste des articles", notes = "Cette méthode permet de de d'afficher la liste des articles existants dans la base de données", responseContainer = "List<ArticleDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des articles / Une liste vide"),
    })
    List<ArticleDto> findAll();


    @DeleteMapping(value = APP_ROOT + "/articles/delete/{idArticle}")
    @ApiOperation(value = "Supprimer un article", notes = "Cette méthode permet la suppression d'un article utilisant son ID",responseContainer = "Void")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a été supprimé"),
    })
    void delete(@PathVariable("idArticle") Integer id);

}