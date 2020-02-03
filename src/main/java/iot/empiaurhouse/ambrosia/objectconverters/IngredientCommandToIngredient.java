package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.IngredientCommand;
import iot.empiaurhouse.ambrosia.model.Ingredient;
import iot.empiaurhouse.ambrosia.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if(ingredientCommand == null){
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientCommand.getId());

        if (ingredientCommand.getRecipeID() != null){
            Recipe recipe = new Recipe();
            recipe.setId(ingredientCommand.getRecipeID());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }
        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setAvgUnitPrice(ingredientCommand.getAvgUnitPrice());
        ingredient.setDescription(ingredientCommand.getDescription());
        ingredient.setAltDescription(ingredientCommand.getAltDescription());
        ingredient.setThumbUrl(ingredientCommand.getThumbUrl());
        ingredient.setUnitOfMeasure(uomConverter.convert(ingredientCommand.getUnitOfMeasure()));
        return ingredient;
    }
}
