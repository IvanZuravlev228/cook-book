package com.cookbookjava.service.mapper;

import com.cookbookjava.model.Recipe;
import com.cookbookjava.model.dto.RecipeRequestDto;
import com.cookbookjava.model.dto.RecipeResponseDto;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {
    public Recipe toModel(RecipeRequestDto dto) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.getName());
        recipe.setInfo(dto.getInfo());
        recipe.setDateOfCreation(LocalDate.now());
        if (dto.getParentId() != null) {
            Recipe parent = new Recipe();
            parent.setId(dto.getParentId());
            recipe.setParent(parent);
        }
        return recipe;
    }

    public RecipeResponseDto toDto(Recipe recipe) {
        RecipeResponseDto dto = new RecipeResponseDto();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setInfo(recipe.getInfo());
        dto.setDateOfCreation(recipe.getDateOfCreation());
        dto.setHistory(recipe.getHistory());
        if (recipe.getParent() != null) {
            dto.setParentId(recipe.getParent().getId());
        }
        return dto;
    }
}
