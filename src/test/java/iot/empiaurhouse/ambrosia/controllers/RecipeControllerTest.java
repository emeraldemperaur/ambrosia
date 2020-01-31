package iot.empiaurhouse.ambrosia.controllers;

import iot.empiaurhouse.ambrosia.commandobjects.RecipeCommand;
import iot.empiaurhouse.ambrosia.model.*;
import iot.empiaurhouse.ambrosia.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
        recipe = new Recipe();
        cuisine = new Cuisine();
        category = new Category();
        recipe.setId(1L);
        recipe.setDescription("Test Recipe");
        recipe.setDirections("Step 1    ,  Step 2 ,   Step 3 , Step 4   , Step 5");
        recipe.setSynopsis(new Synopsis());
        recipe.getIngredients().add(new Ingredient());
        cuisine.setCuisineDescription("Test Cuisine");
        category.setCategoryDescription("Test");
        recipe.getCuisines().add(cuisine);
        recipe.getCategories().add(category);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

    }

    @Test
    public void showById() throws Exception {
        when(recipeService.findById(anyLong())).thenReturn(recipe);
        mockMvc.perform(get("/recipe/modusoperandi/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/modusoperandi"))
                .andExpect(model().attributeExists("recipeItem"))
                .andExpect(model().attributeExists("cuisineTitle"))
                .andExpect(model().attributeExists("categoryTitle"))
                .andExpect(model().attributeExists("directionsList"));

        System.out.println(recipe.getDescription() + " was found w/ the ID: " + recipe.getId().toString() + "!");

        String direction = recipe.getDirections();
        ArrayList<String> directions = new ArrayList<>(Arrays.asList(direction.split("\\s*,\\s*")));
        for (int i=0; i<directions.size(); i++)
        {
            System.out.println("Recipe Directions Iter Test ---> " + directions.get(i));
        }
    }


    @Test
    public void testGetNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();

        mockMvc.perform(get("/recipe/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeeditor"))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("title"))
                .andExpect(model().attributeExists("subtitle"));
    }


    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);
        recipeCommand.setDescription("Test Description");

        when(recipeService.saveRecipeCommandObject(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", recipeCommand.getId().toString())
                .param("description", recipeCommand.getDescription())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/modusoperandi/2"));
    }

    @Test
    public void testUpdateView() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipe/edit/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeeditor"))
                .andExpect(model().attributeExists("recipe"));
    }


    @Test
    public void testDeleteRecipe() throws Exception{
        mockMvc.perform(get("/recipe/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(anyLong());

    }











}