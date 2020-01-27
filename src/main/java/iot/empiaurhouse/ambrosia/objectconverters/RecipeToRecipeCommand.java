package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.RecipeCommand;
import iot.empiaurhouse.ambrosia.model.Category;
import iot.empiaurhouse.ambrosia.model.Cuisine;
import iot.empiaurhouse.ambrosia.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CuisineToCuisineCommand cuisineCommandConverter;
    private final CategoryToCategoryCommand categoryCommandConverter;
    private final  SynopsisToSynopsisCommand synopsisCommandConverter;
    private final IngredientToIngredientCommand ingredientCommandConverter;

    public RecipeToRecipeCommand(CuisineToCuisineCommand cuisineCommandConverter,
                                 CategoryToCategoryCommand categoryCommandConverter, SynopsisToSynopsisCommand synopsisCommandConverter, IngredientToIngredientCommand ingredientCommandConverter) {
        this.cuisineCommandConverter = cuisineCommandConverter;
        this.categoryCommandConverter = categoryCommandConverter;
        this.synopsisCommandConverter = synopsisCommandConverter;
        this.ingredientCommandConverter = ingredientCommandConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null){
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipe.getId());
        recipeCommand.setCookTime(recipe.getCookTime());
        recipeCommand.setPrepTime(recipe.getPrepTime());
        recipeCommand.setDescription(recipe.getDescription());
        recipeCommand.setDifficulty(recipe.getDifficulty());
        recipeCommand.setDirections(recipe.getDirections());
        recipeCommand.setServings(recipe.getServings());
        recipeCommand.setSynopsis(synopsisCommandConverter.convert(recipe.getSynopsis()));
        recipeCommand.setOriginator(recipe.getOriginator());
        recipeCommand.setSource(recipe.getSource());
        recipeCommand.setUrl(recipe.getUrl());
        recipeCommand.setPreference(recipe.getPreference());

        if (recipe.getCategories() != null && recipe.getCategories().size() > 0){
            recipe.getCategories()
                    .forEach((Category category) -> recipeCommand.getCategories().add(categoryCommandConverter.convert(category)));
        }

        if (recipe.getCuisines() != null && recipe.getCuisines().size() > 0){
            recipe.getCuisines()
                    .forEach((Cuisine cuisine) -> recipeCommand.getCuisines().add(cuisineCommandConverter.convert(cuisine)));
        }

        if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0){
            recipe.getIngredients()
                    .forEach(ingredient -> recipeCommand.getIngredients().add(ingredientCommandConverter.convert(ingredient)));
        }

        return recipeCommand;
    }
}
