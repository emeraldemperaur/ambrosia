package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.*;
import iot.empiaurhouse.ambrosia.model.Difficulty;
import iot.empiaurhouse.ambrosia.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "Test Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.FAST;
    public static final Integer SERVINGS = Integer.valueOf("21");
    public static final String SOURCE = "Test Source";
    public static final String ORIGINATOR = "Test Origin";
    public static final String TEST_URL = "Test";
    public static final Long CATEGORY_ID1 = 1L;
    public static final Long CATEGORY_ID2 = 2L;
    public static final Long CUISINE_ID1 = 10L;
    public static final Long CUISINE_ID2 = 11L;
    public static final Long INGREDIENT_ID_1 = 3L;
    public static final Long INGREDIENT_ID_2 = 4L;
    public static final Long SYNOPSIS_ID = 9L;

    RecipeCommandToRecipe testConverter;


    @BeforeEach
    void setUp() throws Exception {
        testConverter = new RecipeCommandToRecipe(new CategoryCommandToCategory(), new CuisineCommandToCuisine(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                        new SynopsisCommandToSynopsis());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(testConverter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(testConverter.convert(new RecipeCommand()));
    }

    @Test
    void convert() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setUrl(TEST_URL);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setOriginator(ORIGINATOR);


        SynopsisCommand synopsisCommand = new SynopsisCommand();
        synopsisCommand.setId(SYNOPSIS_ID);

        recipeCommand.setSynopsis(synopsisCommand);

        CuisineCommand cuisine = new CuisineCommand();
        cuisine.setId(CUISINE_ID1);

        CuisineCommand cuisine2 = new CuisineCommand();
        cuisine2.setId(CUISINE_ID2);

        recipeCommand.getCuisines().add(cuisine);
        recipeCommand.getCuisines().add(cuisine2);

        CategoryCommand category = new CategoryCommand();
        category.setId(CATEGORY_ID1);

        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CATEGORY_ID2);

        recipeCommand.getCategories().add(category);
        recipeCommand.getCategories().add(category2);

        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setId(INGREDIENT_ID_1);

        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGREDIENT_ID_2);

        recipeCommand.getIngredients().add(ingredient);
        recipeCommand.getIngredients().add(ingredient2);

        Recipe recipe  = testConverter.convert(recipeCommand);

        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(TEST_URL, recipe.getUrl());
        assertEquals(SYNOPSIS_ID, recipe.getSynopsis().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
        assertEquals(2, recipe.getCuisines().size());
        assertEquals(2, recipe.getCuisines().size());
    }
}