package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.IngredientCommand;
import iot.empiaurhouse.ambrosia.model.Ingredient;
import iot.empiaurhouse.ambrosia.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

    public static final BigDecimal AMOUNT = new BigDecimal(1);
    public static final String DESCRIPTION = "Test Ingredient";
    public static final String ALT_DESCRIPTION = "Mock Ingredient";
    public static final Long UOM_ID = 2L;
    public static final Long ID_VALUE = 1L;
    IngredientToIngredientCommand testConverter;

    @BeforeEach
    void setUp() {
        testConverter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }


    @Test
    public void testNullConvert() throws Exception {
        assertNull(testConverter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(testConverter.convert(new Ingredient()));
    }

    @Test
    void convert() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAltDescription(ALT_DESCRIPTION);
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);

        ingredient.setUnitOfMeasure(unitOfMeasure);
        IngredientCommand ingredientCommand = testConverter.convert(ingredient);
        assert ingredientCommand != null;
        assertEquals(ID_VALUE, ingredientCommand.getId());
        assertNotNull(ingredientCommand.getUnitOfMeasure());
        assertEquals(UOM_ID, ingredientCommand.getUnitOfMeasure().getId());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(ALT_DESCRIPTION, ingredientCommand.getAltDescription());

    }


    @Test
    public void testConvertNullUOM() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAltDescription(ALT_DESCRIPTION);

        ingredient.setUnitOfMeasure(null);

        IngredientCommand ingredientCommand = testConverter.convert(ingredient);
        assert ingredientCommand != null;
        assertNull(ingredientCommand.getUnitOfMeasure());
        assertEquals(ID_VALUE, ingredientCommand.getId());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertEquals(ALT_DESCRIPTION, ingredientCommand.getAltDescription());
    }

}