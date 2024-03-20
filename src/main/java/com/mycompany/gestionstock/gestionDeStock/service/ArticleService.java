package com.mycompany.gestionstock.gestionDeStock.service;

import com.mycompany.gestionstock.gestionDeStock.dto.ArticleDto;
import com.mycompany.gestionstock.gestionDeStock.model.Article;

import java.util.List;

public interface ArticleService {

    ArticleDto save(ArticleDto dto);

    ArticleDto findById(Integer id);

    ArticleDto findByCodeArticle(String codeArticle);

    List<ArticleDto> findAll();

    void delete(Integer id);

}
