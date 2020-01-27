package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.RecipeCommand;
import iot.empiaurhouse.ambrosia.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {

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
    RecipeToRecipeCommand testConverter;

    @BeforeEach
    void setUp() throws Exception {
        testConverter = new RecipeToRecipeCommand(
                new CuisineToCuisineCommand(),
                new CategoryToCategoryCommand(),
                new SynopsisToSynopsisCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand())
        );
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(testConverter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(testConverter.convert(new Recipe()));
    }


    @Test
    void convert() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(TEST_URL);
        recipe.setOriginator(ORIGINATOR);

        Synopsis synopsis = new Synopsis();
        synopsis.setId(SYNOPSIS_ID);

        recipe.setSynopsis(synopsis);

        Category category = new Category();
        category.setId(CATEGORY_ID1);

        Category category2 = new Category();
        category2.setId(CATEGORY_ID2);

        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);

        Cuisine cuisine = new Cuisine();
        cuisine.setId(CUISINE_ID1);

        Cuisine cuisine2 = new Cuisine();
        cuisine2.setId(CUISINE_ID2);

        recipe.getCuisines().add(cuisine);
        recipe.getCuisines().add(cuisine2);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID_1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGREDIENT_ID_2);

        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient2);

        RecipeCommand command = testConverter.convert(recipe);

        assertNotNull(command);
        assertEquals(RECIPE_ID, command.getId());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(SOURCE, command.getSource());
        assertEquals(TEST_URL, command.getUrl());
        assertEquals(SYNOPSIS_ID, command.getSynopsis().getId());
        assertEquals(2, command.getCategories().size());
        assertEquals(2, command.getIngredients().size());
        assertEquals(2, command.getCuisines().size());

    }
}