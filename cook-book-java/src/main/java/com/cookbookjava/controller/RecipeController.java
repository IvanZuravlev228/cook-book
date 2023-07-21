package com.cookbookjava.controller;

import com.cookbookjava.model.dto.RecipeRequestDto;
import com.cookbookjava.model.dto.RecipeResponseDto;
import com.cookbookjava.service.RecipeService;
import com.cookbookjava.service.mapper.RecipeMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;

    @GetMapping
    public List<RecipeResponseDto> getAllRecipes() {
        return recipeService.getAllRecipes().stream()
                .map(recipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public RecipeResponseDto createRecipe(@RequestBody RecipeRequestDto dto) {
        return recipeMapper.toDto(recipeService.createRecipe(recipeMapper.toModel(dto)));
    }

    @PostMapping("/{parentId}/children")
    public RecipeResponseDto createChildRecipe(@PathVariable Long parentId,
                                               @RequestBody RecipeRequestDto childDto) {
        return recipeMapper.toDto(
                recipeService.createChildRecipe(parentId, recipeMapper.toModel(childDto)));
    }

    @PostMapping("/update/{parentId}")
    public void update(@PathVariable Long parentId,
                       @RequestBody RecipeRequestDto dto) {
        recipeService.update(recipeMapper.toModel(dto), parentId);
    }

    @GetMapping("/child/{id}")
    public List<RecipeResponseDto> findChildrenByParentId(@PathVariable Long id) {
        return recipeService.findChildrenByParentId(id).stream()
                .map(recipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/history/{parentId}")
    public List<RecipeResponseDto> getHistoryBranch(@PathVariable Long parentId) {
        return recipeService.getHistory(parentId).stream()
                .map(recipeMapper::toDto)
                .collect(Collectors.toList());
    }
}
