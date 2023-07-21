package com.cookbookjava.repository;

import com.cookbookjava.model.Recipe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAllByParentIsNullAndChangedIsFalseOrderByName();

    List<Recipe> findAllByParent_IdAndChangedFalseOrderByName(Long parentId);
}
