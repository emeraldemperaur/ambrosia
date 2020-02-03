package iot.empiaurhouse.ambrosia.controllers;

import iot.empiaurhouse.ambrosia.commandobjects.IngredientCommand;
import iot.empiaurhouse.ambrosia.commandobjects.RecipeCommand;
import iot.empiaurhouse.ambrosia.services.IngredientService;
import iot.empiaurhouse.ambrosia.services.RecipeService;
import iot.empiaurhouse.ambrosia.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;
    @Mock
    IngredientService ingredientService;
    IngredientController ingredientController;
    MockMvc mockMvc;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

    }

    @Test
    public void testRecipeIngredients() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredients"))
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService, times(1)).findCommandById(anyLong());
    }


    @Test
    public void testShowIngredient() throws Exception{
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2L);
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setDescription("Test");
        recipeCommand.setId(1L);
        ingredientCommand.setRecipeID(1L);

        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(),anyLong())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1/ingredient/2/"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredient"))
                .andExpect(model().attributeExists("ingredient"));

    }


    @Test
    public void testUpdateIngredientEditor() throws Exception {

        IngredientCommand ingredientCommand = new IngredientCommand();
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        when(unitOfMeasureService.listAllUnitsOfMeasure()).thenReturn(new HashSet<>());
        mockMvc.perform(get("/recipe/1/ingredient/2/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredienteditor"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitsOfMeasureList"));
    }

    @Test
    public void testNewIngredientEditor() throws Exception {

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        when(unitOfMeasureService.listAllUnitsOfMeasure()).thenReturn(new HashSet<>());
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredienteditor"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitsOfMeasureList"));

        verify(recipeService, times(1)).findCommandById(anyLong());

    }



    @Test
    public void testSaveOrUpdate() throws Exception {

        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeID(2L);
        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);
        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "test title")
                .param("altDescription","mock title")
                .param("amount","1")
                .param("avgUnitPrice","20.00")
                .param("thumbUrl","testURL")

        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3"));

    }

    @Test
    public void testDeleteIngredient() throws Exception {

        //then
        mockMvc.perform(get("/recipe/2/ingredient/3/delete")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));

        verify(ingredientService, times(1)).deleteById(anyLong(), anyLong());

    }





}