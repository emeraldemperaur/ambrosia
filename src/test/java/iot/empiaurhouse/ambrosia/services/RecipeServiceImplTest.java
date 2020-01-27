package iot.empiaurhouse.ambrosia.services;

import iot.empiaurhouse.ambrosia.model.Recipe;
import iot.empiaurhouse.ambrosia.objectconverters.RecipeCommandToRecipe;
import iot.empiaurhouse.ambrosia.objectconverters.RecipeToRecipeCommand;
import iot.empiaurhouse.ambrosia.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);

    }

    @Test
    public void getRecipesByIdTest() throws Exception{
        Recipe testRecipe = new Recipe();
        testRecipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(testRecipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        Recipe recipeReturned = recipeService.findById(1L);
        assertNotEquals("Null recipe returned", recipeReturned);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();

    }


    @Test
    public void getRecipes() throws Exception {
        Recipe recipe = new Recipe();
        HashSet recipeDB = new HashSet();
        recipeDB.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipeDB);
        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(1,recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }
}