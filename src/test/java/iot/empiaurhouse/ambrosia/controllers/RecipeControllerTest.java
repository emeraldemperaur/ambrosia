package iot.empiaurhouse.ambrosia.controllers;

import iot.empiaurhouse.ambrosia.model.*;
import iot.empiaurhouse.ambrosia.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    @Mock
    RecipeService recipeService;
    RecipeController recipeController;
    Recipe recipe;
    Cuisine cuisine;
    Category category;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
        recipe = new Recipe();
        cuisine = new Cuisine();
        category = new Category();
        recipe.setId(1L);
        recipe.setDescription("Test Recipe");
        recipe.setDirections(" Step 1 , Step 2 , Step 3 , Step 4 , Step 5");
        recipe.setSynopsis(new Synopsis());
        recipe.getIngredients().add(new Ingredient());
        cuisine.setCuisineDescription("Test Cuisine");
        category.setCategoryDescription("Test");
        recipe.getCuisines().add(cuisine);
        recipe.getCategories().add(category);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

    }

    @Test
    void showById() throws Exception {
        when(recipeService.findById(anyLong())).thenReturn(recipe);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/modusoperandi/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/modusoperandi"))
                .andExpect(model().attributeExists("recipeItem"))
                .andExpect(model().attributeExists("cuisineTitle"))
                .andExpect(model().attributeExists("categoryTitle"))
                .andExpect(model().attributeExists("directionsList"));

        System.out.println(recipe.getDescription() + " was found w/ the ID: " + recipe.getId().toString() + "!");

        String direction = recipe.getDirections();
        ArrayList<String> directions = new ArrayList<>(Arrays.asList(direction.split(",")));
        for (int i=0; i<directions.size(); i++)
        {
            System.out.println("Recipe Directions Iter Test ---> " + directions.get(i));
        }


    }
}