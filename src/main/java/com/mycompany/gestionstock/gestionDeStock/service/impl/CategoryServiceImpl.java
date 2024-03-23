package com.mycompany.gestionstock.gestionDeStock.service.impl;


import com.mycompany.gestionstock.gestionDeStock.dto.CategoryDto;
import com.mycompany.gestionstock.gestionDeStock.exception.EntityNotFoundException;
import com.mycompany.gestionstock.gestionDeStock.exception.ErrorsCodes;
import com.mycompany.gestionstock.gestionDeStock.exception.InvalidEntityException;
import com.mycompany.gestionstock.gestionDeStock.exception.InvalidOperationException;
import com.mycompany.gestionstock.gestionDeStock.model.Article;
import com.mycompany.gestionstock.gestionDeStock.repository.ArticleRepository;
import com.mycompany.gestionstock.gestionDeStock.repository.CategoryRepository;
import com.mycompany.gestionstock.gestionDeStock.service.CategoryService;
import com.mycompany.gestionstock.gestionDeStock.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    @Autowired


    public CategoryDto save(CategoryDto dto) {
        List<String> errors = CategoryValidator.validate(dto);
        if(!errors.isEmpty()) {
            log.error("Catégory is not valid {}", dto);
            throw new InvalidEntityException("La catégorie n'est pas valide", ErrorsCodes.CATEGORY_NOT_VALID, errors);
        }
        return CategoryDto.fromEntity(categoryRepository.save(CategoryDto.toEntity(dto)));
    }

    public CategoryDto findById(Integer id) {
        if(id == null) {
            log.error("Category ID is null");
            return null;
        }
        return categoryRepository.findById(id)
                .map(CategoryDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune category trouvé avec l'ID rentré",
                        ErrorsCodes.CATEGORY_NOT_FOUND)
                );
    }

    public CategoryDto findByCode(String code) {
        if(!StringUtils.hasLength(code)) {
            log.error("Category code is null");
            return null;
        }
        return categoryRepository.findCategoryByCode(code)
                .map(CategoryDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune category trouvé avec le code rentré",
                        ErrorsCodes.CATEGORY_NOT_FOUND)
                );
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    public void delete(Integer id) {
        if (id == null) {
            log.error("Category ID is null");
            return;
        }
        List<Article> articles = articleRepository.findAllByCategoryId(id);
        if(!articles.isEmpty()) {
            throw new InvalidOperationException("Impossible de supprimer cette catégorie, elle est associé à certains articles",
                    ErrorsCodes.CATEGORY_ALREADY_IN_USE);
        }
        categoryRepository.deleteById(id);
    }
}
