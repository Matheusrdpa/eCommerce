package com.estudo.eCommerce.dto;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends ErrorDTO{

    private List<ValidationMessage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public void addError(String validationField, String validationMessage) {
        errors.add(new ValidationMessage(validationField, validationMessage));
    }
}
