package com.cookbookjava.service.mapper;

import com.cookbookjava.model.Recipe;
import com.cookbookjava.model.dto.RecipeRequestDto;
import com.cookbookjava.model.dto.RecipeResponseDto;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper implements
        RequestResponseDtoMapper<RecipeRequestDto, RecipeResponseDto, Recipe> {
    public Recipe mapToModel(RecipeRequestDto dto) {
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

    @Override
    public RecipeResponseDto mapToDto(Recipe recipe) {
        RecipeResponseDto dto = new RecipeResponseDto();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setInfo(recipe.getInfo());
        dto.setDateOfCreation(recipe.getDateOfCreation());
        dto.setPreviousId(recipe.getPreviousId());
        if (recipe.getParent() != null) {
            dto.setParentId(recipe.getParent().getId());
        }
        return dto;
    }
}
