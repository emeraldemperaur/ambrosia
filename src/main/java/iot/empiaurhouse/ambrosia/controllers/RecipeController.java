package iot.empiaurhouse.ambrosia.controllers;

import iot.empiaurhouse.ambrosia.commandobjects.RecipeCommand;
import iot.empiaurhouse.ambrosia.model.Category;
import iot.empiaurhouse.ambrosia.model.Cuisine;
import iot.empiaurhouse.ambrosia.model.Recipe;
import iot.empiaurhouse.ambrosia.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

@Controller
@Slf4j
public class RecipeController {
    private final RecipeService recipeService;



    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/modusoperandi/{id}")
    public String showById(@PathVariable String id, Model model){
        log.debug("Request was made for Recipe w/ UID:" + id);

        model.addAttribute("recipeItem", recipeService.findById(Long.valueOf(id)));
        model.addAttribute("cuisineTitle",getSingleCuisineTitle(id));
        model.addAttribute("categoryTitle",getSingleCategoryTitle(id));
        model.addAttribute("directionsList", directionsList(id));

        return "recipe/modusoperandi";
    }

    @GetMapping("recipe/create")
    public String newRecipe(Model recipeModel){
        recipeModel.addAttribute("recipe", new RecipeCommand());
        recipeModel.addAttribute("title","NEW RECIPE");
        recipeModel.addAttribute("subtitle","ENTER RECIPE DETAILS");

        return "recipe/recipeeditor";
    }

    @GetMapping("recipe/edit/{id}")
    public String editRecipe(@PathVariable String id, Model editRecipeModel){
        editRecipeModel.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        editRecipeModel.addAttribute("title",getEditRecipeTitle(id));
        editRecipeModel.addAttribute("subtitle",getEditRecipeSubTitle(id));


        return "recipe/recipeeditor";
    }



    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand){
        RecipeCommand storedRecipeObject = recipeService.saveRecipeCommandObject(recipeCommand);
        System.out.println("saveOrUpdate() POST!!!");
        return "redirect:/recipe/modusoperandi/" + storedRecipeObject.getId();
    }


    @GetMapping("recipe/delete/{id}")
    public String deleteById(@PathVariable String id){

        log.debug("Deleting id: " + id);

        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    //Dynamic Model Attributes Mill

    private String getSingleCuisineTitle(String recipeID){
        Recipe selectedRecipe = recipeService.findById(Long.valueOf(recipeID));
        Cuisine nullCuisine = new Cuisine();
        nullCuisine.setCuisineDescription("No Description Found For");
        Set<Cuisine> recipeCuisines = selectedRecipe.getCuisines();
        Cuisine cuisineIter = recipeCuisines.stream().findFirst().orElse(nullCuisine);
        return cuisineIter.getCuisineDescription();
    }


    private String getSingleCategoryTitle(String recipeID){
        Recipe selectedRecipe = recipeService.findById(Long.valueOf(recipeID));
        Category nullCategory = new Category();
        nullCategory.setCategoryDescription("No Category");
        Set<Category> recipeCategories = selectedRecipe.getCategories();
        Category categoryIter = recipeCategories.stream().findFirst().orElse(nullCategory);
        return categoryIter.getCategoryDescription();
    }

    private ArrayList<String> directionsList(String recipeID){
        Recipe selectedRecipe = recipeService.findById(Long.valueOf(recipeID));
        String directions = selectedRecipe.getDirections();

        return new ArrayList<>(Arrays.asList(directions.split("\\s*,\\s*")));
    }


    private String getEditRecipeTitle(String recipeID){
        RecipeCommand editRecipe = recipeService.findCommandById(Long.valueOf(recipeID));
        return editRecipe.getDescription();
    }

    private String getEditRecipeSubTitle(String recipeID){
        RecipeCommand editRecipe = recipeService.findCommandById(Long.valueOf(recipeID));
        return "EDIT " + editRecipe.getDescription() + " RECIPE DETAILS";
    }


}
