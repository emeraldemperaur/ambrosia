package iot.empiaurhouse.ambrosia.controllers;

import iot.empiaurhouse.ambrosia.commandobjects.IngredientCommand;
import iot.empiaurhouse.ambrosia.commandobjects.RecipeCommand;
import iot.empiaurhouse.ambrosia.commandobjects.UnitOfMeasureCommand;
import iot.empiaurhouse.ambrosia.services.IngredientService;
import iot.empiaurhouse.ambrosia.services.RecipeService;
import iot.empiaurhouse.ambrosia.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;


    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }




    @GetMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model){
        log.debug("Fetching List <Ingredients> for Recipe id: " + id);
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/ingredient/ingredients";
    }


    @GetMapping({"recipe/{recipeId}/ingredient/{ingredientId}/","recipe/{recipeId}/ingredient/{ingredientId}",
            "recipe/{recipeId}/ingredient/{ingredientId}/view"})
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId)
                , Long.valueOf(ingredientId)));
        model.addAttribute("recipeTitle",getEditIngredientRecipeTitle(recipeId, ingredientId));
        return "recipe/ingredient/ingredient";
    }


    @GetMapping("recipe/{recipeId}/ingredient/{id}/edit")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        model.addAttribute("title", getEditIngredientTitle(recipeId,id));
        model.addAttribute("subtitle", getEditIngredientSubTitle(recipeId,id));
        model.addAttribute("buttonText", "UPDATE INGREDIENT");
        model.addAttribute("unitsOfMeasureList", unitOfMeasureService.listAllUnitsOfMeasure());
        return "recipe/ingredient/ingredienteditor";
    }

    @GetMapping({"recipe/{recipeId}/ingredient/add","recipe/{recipeId}/ingredient/new"})
    public String newIngredient(@PathVariable String recipeId, Model model){

        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeID(Long.valueOf(recipeId));
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("title", "NEW INGREDIENT");
        model.addAttribute("subtitle", "ENTER INGREDIENT DETAILS");
        model.addAttribute("buttonText", "ADD INGREDIENT");
        model.addAttribute("unitsOfMeasureList",  unitOfMeasureService.listAllUnitsOfMeasure());
        return "recipe/ingredient/ingredienteditor";
    }


    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        log.debug("Saved Ingredient w/ Recipe ID: " + savedCommand.getRecipeID());
        log.debug("Saved Ingredient w/ ID: " + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeID() + "/ingredient/" + savedCommand.getId() ;
    }



    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String id){

        log.debug("deleting ingredient id:" + id);
        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }



    //Dynamic Model Attributes Mill

    private String getEditIngredientTitle(String recipeID, String ingredientID){
        IngredientCommand selectedIngredient = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeID), Long.valueOf(ingredientID));
        return selectedIngredient.getDescription();
    }

    private String getEditIngredientSubTitle(String recipeID, String ingredientID){
        IngredientCommand selectedIngredient = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeID), Long.valueOf(ingredientID));
        String ingredientTitle = selectedIngredient.getDescription();
        return "EDIT " + selectedIngredient.getDescription() + " DETAILS";
    }


    public String getEditIngredientRecipeTitle(String recipeID, String ingredientID){
        IngredientCommand selectedIngredient = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeID), Long.valueOf(ingredientID));
        Long recipeId = selectedIngredient.getRecipeID();
        RecipeCommand focusRecipe = recipeService.findCommandById(recipeId);
        String outputString;
        if(focusRecipe == null){
            outputString = "Corrupted Recipe Object";
        }
        else {
            outputString = focusRecipe.getDescription();
        }

        return outputString;
    }


}
