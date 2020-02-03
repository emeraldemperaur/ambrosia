package iot.empiaurhouse.ambrosia.services;

import iot.empiaurhouse.ambrosia.commandobjects.IngredientCommand;
import iot.empiaurhouse.ambrosia.model.Ingredient;
import iot.empiaurhouse.ambrosia.model.Recipe;
import iot.empiaurhouse.ambrosia.objectconverters.IngredientCommandToIngredient;
import iot.empiaurhouse.ambrosia.objectconverters.IngredientToIngredientCommand;
import iot.empiaurhouse.ambrosia.objectconverters.UnitOfMeasureCommandToUnitOfMeasure;
import iot.empiaurhouse.ambrosia.objectconverters.UnitOfMeasureToUnitOfMeasureCommand;
import iot.empiaurhouse.ambrosia.repositories.RecipeRepository;
import iot.empiaurhouse.ambrosia.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;

    IngredientServiceImplTest() {
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
                recipeRepository, unitOfMeasureRepository);
    }

    @Test
    void findByRecipeIdAndIngredientId() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Ingredient testIngredient = new Ingredient();
        testIngredient.setId(1L);
        Ingredient mockIngredient = new Ingredient();
        mockIngredient.setId(2L);
        Ingredient nullIngredient = new Ingredient();
        nullIngredient.setId(3L);

        recipe.addIngredient(testIngredient);
        recipe.addIngredient(mockIngredient);
        recipe.addIngredient(nullIngredient);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeID());

    }


    @Test
    public void testSaveByRecipeCommand() throws Exception{
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeID(2L);
        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
        Recipe storedRecipe = new Recipe();
        storedRecipe.addIngredient(new Ingredient());
        storedRecipe.getIngredients().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(storedRecipe);

        IngredientCommand storedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        assertEquals(Long.valueOf(3L), storedIngredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }


    @Test
    public void testDeleteById() throws Exception {
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        ingredientService.deleteById(1L, 3L);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }





}