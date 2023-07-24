package com.cookbookjava.service.impl;

import com.cookbookjava.model.Recipe;
import com.cookbookjava.repository.RecipeRepository;
import com.cookbookjava.service.RecipeService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository repository;

    @Override
    public List<Recipe> getAllRecipes() {
        return repository.findAllByParentIsNullAndChangedIsFalseOrderByName();
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find recipe by id: " + id));
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        return repository.save(recipe);
    }

    @Override
    public Recipe createChildRecipe(Long parentId, Recipe child) {
        Recipe parent = getRecipeById(parentId);
        child.setParent(parent);
        return repository.save(child);
    }

    @Override
    public List<Recipe> findChildrenByParentId(Long parentId) {
        return repository.findAllByParent_IdAndChangedFalseOrderByName(parentId);
    }

    @Override
    public void update(Recipe newRecipe, Long oldRecipeId) {
        newRecipe.setPreviousId(oldRecipeId);
        Recipe recipeNew = createRecipe(newRecipe);
        Recipe recipeOld = getRecipeById(oldRecipeId);
        recipeOld.setChanged(true);
        repository.save(recipeOld);
        List<Recipe> childrenByParentId = findChildrenByParentId(oldRecipeId);
        for (Recipe recipe : childrenByParentId) {
            recipe.setParent(recipeNew);
            repository.save(recipe);
        }
    }

    @Override
    public List<Recipe> getHistory(Long parentId) {
        List<Recipe> result = new ArrayList<>();
        Recipe parent = getRecipeById(parentId);
        Long previousId = parent.getPreviousId();
        result.add(parent);
        while (previousId != null) {
            Recipe previousRecipe = getRecipeById(previousId);
            previousId = previousRecipe.getPreviousId();
            result.add(previousRecipe);
        }
        return result;
    }
}
