package com.cookbookjava.config;

import com.cookbookjava.model.Recipe;
import com.cookbookjava.service.RecipeService;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final RecipeService service;

    public DataInitializer(RecipeService service) {
        this.service = service;
    }

    @PostConstruct
    public void inject() {
        Recipe chicken = new Recipe();
        chicken.setParent(null);
        chicken.setName("chicken");
        chicken.setInfo("Some text about recipe");
        chicken.setDateOfCreation(LocalDate.now());
        chicken = service.createRecipe(chicken);

        Recipe sauce = new Recipe();
        sauce.setParent(chicken);
        sauce.setName("sauce");
        sauce.setInfo("Some text about recipe");
        sauce.setDateOfCreation(LocalDate.now());
        service.createRecipe(sauce);

        Recipe ketchup = new Recipe();
        ketchup.setParent(chicken);
        ketchup.setName("ketchup");
        ketchup.setInfo("Some text about recipe");
        ketchup.setDateOfCreation(LocalDate.now());
        service.createRecipe(ketchup);

        Recipe onion = new Recipe();
        onion.setParent(sauce);
        onion.setName("onion");
        onion.setInfo("Some text about recipe");
        onion.setDateOfCreation(LocalDate.now());
        service.createRecipe(onion);

        Recipe beef = new Recipe();
        beef.setParent(sauce);
        beef.setName("beef");
        beef.setInfo("Some text about recipe");
        beef.setDateOfCreation(LocalDate.now());
        service.createRecipe(beef);
    }
}
