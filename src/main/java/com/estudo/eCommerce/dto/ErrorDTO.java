package com.estudo.eCommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorDTO {
    private Instant timeStamp;
    private Integer status;
    private String error;
    private String path;
}
