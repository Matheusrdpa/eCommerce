package com.estudo.eCommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationMessage {
    private String validationField;
    private String ValidationMessage;
}
