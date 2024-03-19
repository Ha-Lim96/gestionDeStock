package com.mycompany.gestionstock.gestionDeStock.handlers;


import com.mycompany.gestionstock.gestionDeStock.exception.ErrorsCodes;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {

    private Integer httpCode;

    private ErrorsCodes code;

    private String message;

    private List<String> errors = new ArrayList<>();


}
