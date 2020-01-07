package iot.empiaurhouse.ambrosia.controllers;

import iot.empiaurhouse.ambrosia.model.*;
import iot.empiaurhouse.ambrosia.repositories.CategoryRepository;
import iot.empiaurhouse.ambrosia.repositories.RecipeRepository;
import iot.empiaurhouse.ambrosia.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;


    public IndexController(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(){

        Optional<Category> categoryOptional = categoryRepository.findByCategoryDescription("Lunch");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByUom("Pound");

        Recipe eguisi = new Recipe();
        Synopsis synopsis = new Synopsis();
        Cuisine chinese = new Cuisine();
        chinese.setCuisineDescription("Chinese");
        synopsis.setRecipeSynopsis("Eguisi");
        eguisi.setDescription("Eguisi");
        eguisi.getCuisines().add(chinese);


        System.out.println("Recipe Category ID is: " + categoryOptional.get().getId());
        System.out.println("UOM ID is: " + unitOfMeasureOptional.get().getId());
        System.out.println(eguisi.getDescription());
        System.out.println(eguisi.getCuisines());
        return "index";
    }

}
