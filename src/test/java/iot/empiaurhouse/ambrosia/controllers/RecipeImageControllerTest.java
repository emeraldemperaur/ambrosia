package iot.empiaurhouse.ambrosia.controllers;

import iot.empiaurhouse.ambrosia.commandobjects.RecipeCommand;
import iot.empiaurhouse.ambrosia.services.ImageService;
import iot.empiaurhouse.ambrosia.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeImageControllerTest {

    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;
    RecipeImageController recipeImageController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeImageController = new RecipeImageController(imageService,recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeImageController).build();

    }

    @Test
    void renderImageUploader() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        recipeCommand.setDescription("Test Recipe");
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipe/1/img"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("buttonText"))
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService, times(1)).findCommandById(anyLong());


    }

    @Test
    void handleImagePost() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("recipeImgFile","test.txt","text/plain","test".getBytes());
        mockMvc.perform(multipart("/recipe/1/img").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location","/recipe/modusoperandi/1"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());


    }


    @Test
    public void renderMultipartImageFile() throws Exception {

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        String s = "Mock Multipart Image File";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];
        int i = 0;
        for (byte primByte : s.getBytes()){
            bytesBoxed[i++] = primByte;
        }
        recipeCommand.setImage(bytesBoxed);
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/getimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseByte = response.getContentAsByteArray();

        assertEquals(s.getBytes().length, responseByte.length);
    }


}



