package com.cookbookjava.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RecipeRequestDto {
    private String name;
    private String info;
    private Long parentId;
}
