package iot.empiaurhouse.ambrosia.bootstrap;

import iot.empiaurhouse.ambrosia.model.*;
import iot.empiaurhouse.ambrosia.repositories.CategoryRepository;
import iot.empiaurhouse.ambrosia.repositories.CuisineRepository;
import iot.empiaurhouse.ambrosia.repositories.RecipeRepository;
import iot.empiaurhouse.ambrosia.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;
    private CuisineRepository cuisineRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeDataBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
                               CuisineRepository cuisineRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.cuisineRepository = cuisineRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes(){

        List<Recipe> recipes = new ArrayList<>();

        Optional<UnitOfMeasure> ounceUoMOptional = unitOfMeasureRepository.findByUom("Ounce");
        if (!ounceUoMOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> poundUoMOptional = unitOfMeasureRepository.findByUom("Pound");
        if (!poundUoMOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> pinchUoMOptional = unitOfMeasureRepository.findByUom("Pinch");
        if (!pinchUoMOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> cupUoMOptional = unitOfMeasureRepository.findByUom("Cup");
        if (!cupUoMOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> cubeUoMOptional = unitOfMeasureRepository.findByUom("Cube");
        if (!cubeUoMOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> tablespoonUoMOptional = unitOfMeasureRepository.findByUom("Tablespoon");
        if (!tablespoonUoMOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> teaspoonUoMOptional = unitOfMeasureRepository.findByUom("Teaspoon");
        if (!teaspoonUoMOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }


        //UoM(s) Init
        UnitOfMeasure ounceUoM = ounceUoMOptional.get();
        UnitOfMeasure poundUoM = poundUoMOptional.get();
        UnitOfMeasure pinchUoM = pinchUoMOptional.get();
        UnitOfMeasure cupUoM = cupUoMOptional.get();
        UnitOfMeasure cubeUoM = cubeUoMOptional.get();
        UnitOfMeasure tablespoonUoM = tablespoonUoMOptional.get();
        UnitOfMeasure teaspoonUoM = teaspoonUoMOptional.get();

        //Category container(s)
        Optional<Category> breakfastCategoryOptional = categoryRepository.findByCategoryDescription("Breakfast");
        if (!breakfastCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> lunchCategoryOptional = categoryRepository.findByCategoryDescription("Lunch");
        if (!lunchCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> dinnerCategoryOptional = categoryRepository.findByCategoryDescription("Dinner");
        if (!dinnerCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> snackCategoryOptional = categoryRepository.findByCategoryDescription("Snack");
        if (!snackCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        //Cuisines container
        Optional<Cuisine> nigerianCuisineOptional = cuisineRepository.findByCuisineDescription("Nigerian");
        if (!nigerianCuisineOptional.isPresent()){
            throw new RuntimeException("Expected Cuisine Not Found");
        }

        Optional<Cuisine> indianCuisineOptional = cuisineRepository.findByCuisineDescription("Indian");
        if (!indianCuisineOptional.isPresent()){
            throw new RuntimeException("Expected Cuisine Not Found");
        }

        Category breakfastCategory = breakfastCategoryOptional.get();
        Category lunchCategory = lunchCategoryOptional.get();
        Category dinnerCategory = dinnerCategoryOptional.get();
        Category snackCategory = snackCategoryOptional.get();

        Cuisine nigerianCuisine = nigerianCuisineOptional.get();
        Cuisine indianCuisine = indianCuisineOptional.get();


        //Eguisi bootstrap
        Recipe eguisi = new Recipe();
        eguisi.setDescription("Eguisi");
        eguisi.setPrepTime(20);
        eguisi.setCookTime(30);
        eguisi.setDifficulty(Difficulty.EASY);
        eguisi.setDirections("1>_ Boil the meat stock till soft using seasoning mix of pepper, onions, salt and thyme leaves\n"
        + "2A>_ Add Palm Oil to boiling pot, then add chopped onions. Then stir for a bit until it becomes a paste.\n" +
                        "2B>_ Add Eguisi to the palm oil paste, then stir for a bit\n" +
                "3>_ Add meat stock to the palm oil paste, then stir for a bit\n" +
                "4>_ Blend crayfish & pepper with water to create a seasoning mix. Add crayfish-pepper season mix to the pot. Then stir for a bit\n" +
                "5>_ Add Salt & Knor/Maggi cubes to season the pot mix to taste\n" +
                "6>_ Add chopped/sliced okro into the pot mix, then boil for a short while.\n" +
                "7>_ Add Ugwu/Spinach into the pot mix, then stir for a bit. Season to taste if needed\n");

        Synopsis eguisiSynopsis = new Synopsis();
        eguisiSynopsis.setRecipeSynopsis("The Nigerian Egusi soup, prepared with melon seeds, is prepared by most tribes in Nigeria in many different ways.\n It is known as Miyan Gushi in Hausa, Ofe Egusi in Igbo and Efo Elegusi in Yoruba.");
        eguisi.setSynopsis(eguisiSynopsis);



        Ingredient eguisiSeed = new Ingredient();
        eguisiSeed.setDescription("Eguisi");
        eguisiSeed.setAltDescription("Melon Seed");
        eguisiSeed.setAmount(new BigDecimal(3));
        eguisiSeed.setUnitOfMeasure(cupUoM);

        Ingredient palmOil = new Ingredient();
        palmOil.setDescription("Palm Oil");
        palmOil.setAltDescription("Palm Oil");
        palmOil.setAmount(new BigDecimal(1));
        palmOil.setUnitOfMeasure(cupUoM);

        Ingredient crayFish = new Ingredient();
        crayFish.setDescription("Crayfish");
        crayFish.setAltDescription("Crawfish");
        crayFish.setAmount(new BigDecimal(1));
        crayFish.setUnitOfMeasure(poundUoM);

        Ingredient pepper = new Ingredient();
        pepper.setDescription("Pepper");
        pepper.setAltDescription("Pepper");
        pepper.setAmount(new BigDecimal(3));
        pepper.setUnitOfMeasure(tablespoonUoM);

        Ingredient ugwu = new Ingredient();
        ugwu.setDescription("Ugwu");
        ugwu.setAltDescription("Spinach");
        ugwu.setAmount(new BigDecimal(1));
        ugwu.setUnitOfMeasure(poundUoM);

        Ingredient maggi = new Ingredient();
        maggi.setDescription("Maggi");
        maggi.setAltDescription("Knor");
        maggi.setAmount(new BigDecimal(3));
        maggi.setUnitOfMeasure(cubeUoM);

        Ingredient thyme = new Ingredient();
        thyme.setDescription("Thyme");
        thyme.setAltDescription("Thyme");
        thyme.setAmount(new BigDecimal(5));
        thyme.setUnitOfMeasure(pinchUoM);

        Ingredient onions = new Ingredient();
        onions.setDescription("Onions");
        onions.setAltDescription("Onions");
        onions.setAmount(new BigDecimal(1));
        onions.setUnitOfMeasure(poundUoM);

        Ingredient salt = new Ingredient();
        salt.setDescription("Salt");
        salt.setAltDescription("Salt");
        salt.setAmount(new BigDecimal(4));
        salt.setUnitOfMeasure(teaspoonUoM);

        Ingredient beef = new Ingredient();
        beef.setDescription("Beef");
        beef.setAltDescription("Beef");
        beef.setAmount(new BigDecimal(2));
        beef.setUnitOfMeasure(poundUoM);

        Ingredient gizzard = new Ingredient();


        eguisi.getIngredients().add(palmOil);
        eguisi.getIngredients().add(crayFish);
        eguisi.getIngredients().add(pepper);
        eguisi.getIngredients().add(ugwu);
        eguisi.getIngredients().add(maggi);
        eguisi.getIngredients().add(thyme);
        eguisi.getIngredients().add(onions);
        eguisi.getIngredients().add(salt);
        eguisi.getIngredients().add(beef);
        eguisi.getIngredients().add(gizzard);
        eguisi.getCategories().add(breakfastCategory);
        eguisi.getCategories().add(lunchCategory);
        eguisi.getCategories().add(dinnerCategory);
        eguisi.getCategories().add(snackCategory);
        eguisi.getCuisines().add(nigerianCuisine);
        eguisi.getCuisines().add(indianCuisine);

        recipes.add(eguisi);






        return recipes;
    }



}
