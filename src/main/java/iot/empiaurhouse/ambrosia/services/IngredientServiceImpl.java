package iot.empiaurhouse.ambrosia.services;

import iot.empiaurhouse.ambrosia.commandobjects.IngredientCommand;
import iot.empiaurhouse.ambrosia.model.Ingredient;
import iot.empiaurhouse.ambrosia.model.Recipe;
import iot.empiaurhouse.ambrosia.objectconverters.IngredientCommandToIngredient;
import iot.empiaurhouse.ambrosia.objectconverters.IngredientToIngredientCommand;
import iot.empiaurhouse.ambrosia.repositories.RecipeRepository;
import iot.empiaurhouse.ambrosia.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(!recipeOptional.isPresent()){
            log.error("Recipe ID not found. No recipe exists w/ ID: " + recipeId);
        }
        Recipe foundRecipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = foundRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();
        if (!ingredientCommandOptional.isPresent()){
            log.error("Ingredient ID " + ingredientId + "not found!"  );
        }

        IngredientCommand ingredientCommand = ingredientCommandOptional.get();
        return ingredientCommand;
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeID());
        if(!recipeOptional.isPresent()){
            log.error("Recipe w/ ID:" + ingredientCommand.getRecipeID());
            return new IngredientCommand();

        }else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setAltDescription(ingredientCommand.getAltDescription());
                ingredient.setAmount(ingredientCommand.getAmount());
                ingredient.setAvgUnitPrice(ingredientCommand.getAvgUnitPrice());
                ingredient.setThumbUrl(ingredientCommand.getThumbUrl());
                ingredient.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
            }else {
                Ingredient newIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
                assert newIngredient != null;
                newIngredient.setRecipe(recipe);
                recipe.addIngredient(Objects.requireNonNull(newIngredient));
            }

            Recipe storedRecipe = recipeRepository.save(recipe);
            Optional<Ingredient> storedIngredientOptional = storedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(!storedIngredientOptional.isPresent()){
                storedIngredientOptional = storedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAltDescription().equals(ingredientCommand.getAltDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            return ingredientToIngredientCommand.convert(storedIngredientOptional.get());

        }



    }

    @Override
    public void deleteById(Long recipeId, Long ingredientIdToDelete) {
        log.debug("Deleting Ingredient w/ Recipe ID: " + recipeId + "and Ingredient ID: " + ingredientIdToDelete);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isPresent()){
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientIdToDelete))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            }
        } else {
            log.debug("Recipe w/ ID: " +  recipeId + " not found!");
        }
    }
}
