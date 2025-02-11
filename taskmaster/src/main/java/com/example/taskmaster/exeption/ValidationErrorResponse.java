package com.example.taskmaster.exeption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidationErrorResponse {

    private String field;
    private String message;

}
