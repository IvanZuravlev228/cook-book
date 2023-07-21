package com.cookbookjava.model.dto;

import java.time.LocalDate;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RecipeResponseDto {
    private Long id;
    private String name;
    private LocalDate dateOfCreation;
    private String info;
    private Long parentId;
    private Long history;
}
