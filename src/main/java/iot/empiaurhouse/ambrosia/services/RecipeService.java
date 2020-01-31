package iot.empiaurhouse.ambrosia.services;

import iot.empiaurhouse.ambrosia.commandobjects.RecipeCommand;
import iot.empiaurhouse.ambrosia.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById (Long l);

    RecipeCommand findCommandById(Long l);

    RecipeCommand saveRecipeCommandObject(RecipeCommand recipeCommand);

    void deleteById(Long id);
}
