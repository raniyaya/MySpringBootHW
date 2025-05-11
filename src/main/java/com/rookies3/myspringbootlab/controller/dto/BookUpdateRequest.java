package com.rookies3.myspringbootlab.controller.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookUpdateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotNull
    private Integer price;

    @NotNull
    private LocalDate publishDate;
}
