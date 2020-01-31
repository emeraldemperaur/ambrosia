package iot.empiaurhouse.ambrosia.controllers;

import iot.empiaurhouse.ambrosia.services.IngredientService;
import iot.empiaurhouse.ambrosia.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }



    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model){
        log.debug("Fetching List <Ingredients> for Recipe id: " + id);
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/ingredient/ingredients";
    }

    @GetMapping
    @RequestMapping({"recipe/{recipeId}/ingredient/{ingredientId}/","recipe/{recipeId}/ingredient/{ingredientId}",
            "recipe/{recipeId}/ingredient/{ingredientId}/view"})
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId)
                , Long.valueOf(ingredientId)));
        return "recipe/ingredient/ingredient";
    }



}
