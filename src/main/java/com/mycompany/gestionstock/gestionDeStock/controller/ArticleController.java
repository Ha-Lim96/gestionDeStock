package com.mycompany.gestionstock.gestionDeStock.controller;

import com.mycompany.gestionstock.gestionDeStock.controller.api.ArticleApi;
import com.mycompany.gestionstock.gestionDeStock.dto.ArticleDto;
import com.mycompany.gestionstock.gestionDeStock.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController implements ArticleApi {

    @Autowired
    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    public ArticleDto save(ArticleDto dto) {
        return articleService.save(dto);
    }


    public ArticleDto findById(Integer id) {
        return articleService.findById(id);
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        return articleService.findByCodeArticle(codeArticle);
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleService.findAll();
    }

    @Override
    public void delete(Integer id) {
        articleService.delete(id);
    }
}
