package panzer.leopard2.openai2.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import panzer.leopard2.openai2.entity.RecipeDTO;
import panzer.leopard2.openai2.service.RecipeService;

import java.io.IOException;
import java.util.Map;

@RestController
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/recipe")
    public Map<String, Object> recipe(@RequestBody RecipeDTO recipe) throws IOException {
        return recipeService.createRecipeWithUrls(recipe);
    }
}
