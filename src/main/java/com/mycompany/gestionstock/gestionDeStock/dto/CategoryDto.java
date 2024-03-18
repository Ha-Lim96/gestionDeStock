package com.mycompany.gestionstock.gestionDeStock.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.gestionstock.gestionDeStock.model.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryDto {

    private Integer id;

    private String code;

    private String designation;

    private Integer idEntreprise;

    @JsonIgnore
    private List<ArticleDto> articles;


    // Mapping de Category ==> CategoryDto
    public static CategoryDto fromEntity(Category category) {

      if (category == null) {
          return null ;
          // TODO Throw an exception
      }

      return CategoryDto.builder()
              .id(category.getId())
              .code(category.getCode())
              .designation(category.getDesignation()).build();
    }


  // Mapping de CategoryDto ==> Category
  public static Category toEntity(CategoryDto categoryDto) {

    if (categoryDto == null) {
      return null ;
      // TODO Throw an exception
    }

    Category category = new Category();
    category.setId(categoryDto.getId());
    category.setCode(categoryDto.getCode());
    category.setDesignation(categoryDto.getDesignation());

    return category;
  }

}
