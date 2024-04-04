package com.mycompany.gestionstock.gestionDeStock.controller.api;

import com.mycompany.gestionstock.gestionDeStock.dto.CategoryDto;
import io.swagger.annotations.Api;

import java.util.List;

import static com.mycompany.gestionstock.gestionDeStock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/categories")
public interface CategoryApi {

    CategoryDto save(CategoryDto dto);

    CategoryDto findById(Integer id);

    CategoryDto findByCode(String code);

    List<CategoryDto> findAll();

    void delete(Integer id);
}
