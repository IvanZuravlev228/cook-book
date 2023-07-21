package com.cookbookjava.service;

import com.cookbookjava.model.Recipe;
import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    Recipe getRecipeById(Long id);

    Recipe createRecipe(Recipe recipe);

    Recipe createChildRecipe(Long parentId, Recipe child);

    List<Recipe> findChildrenByParentId(Long parentId);

    void update(Recipe newRecipe, Long oldRecipeId);

    List<Recipe> getHistory(Long parentId);

}
