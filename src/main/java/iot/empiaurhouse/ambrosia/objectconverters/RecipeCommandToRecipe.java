package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.RecipeCommand;
import iot.empiaurhouse.ambrosia.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConverter;
    private final CuisineCommandToCuisine cuisineConverter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final SynopsisCommandToSynopsis synopsisConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter, CuisineCommandToCuisine cuisineConverter,
                                 IngredientCommandToIngredient ingredientConverter, SynopsisCommandToSynopsis synopsisConverter) {
        this.categoryConverter = categoryConverter;
        this.cuisineConverter = cuisineConverter;
        this.ingredientConverter = ingredientConverter;
        this.synopsisConverter = synopsisConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if (recipeCommand == null){
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setDirections(recipeCommand.getDirections());
        recipe.setDifficulty(recipeCommand.getDifficulty());
        recipe.setServings(recipeCommand.getServings());
        recipe.setOriginator(recipeCommand.getOriginator());
        recipe.setSource(recipeCommand.getSource());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setPreference(recipeCommand.getPreference());
        recipe.setSynopsis(synopsisConverter.convert(recipeCommand.getSynopsis()));

        if (recipeCommand.getCategories() != null && recipeCommand.getCategories().size() > 0){
            recipeCommand.getCategories()
                    .forEach(categoryCommand -> recipe.getCategories().add(categoryConverter.convert(categoryCommand)));
        }

        if (recipeCommand.getCuisines() != null && recipeCommand.getCuisines().size() > 0){
            recipeCommand.getCuisines()
                    .forEach(cuisineCommand -> recipe.getCuisines().add(cuisineConverter.convert(cuisineCommand)));
        }

        if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0){
            recipeCommand.getIngredients()
                    .forEach(ingredientCommand -> recipe.getIngredients().add(ingredientConverter.convert(ingredientCommand)));
        }

        return recipe;
    }
}
