package com.example.taskmaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskRequest (

        @NotBlank(message = "Title is mandatory")
        @Size(max = 100, message = "Title must be less than 100 characters")
        String title,

        @NotBlank(message = "Description is mandatory")
        String description,

        @NotNull(message = "Due date is mandatory")
        LocalDate dueDate,

        boolean completed

        ) {
}
