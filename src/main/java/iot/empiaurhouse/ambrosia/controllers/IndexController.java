package iot.empiaurhouse.ambrosia.controllers;

import iot.empiaurhouse.ambrosia.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(Model recipeModel){
        recipeModel.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }

}
