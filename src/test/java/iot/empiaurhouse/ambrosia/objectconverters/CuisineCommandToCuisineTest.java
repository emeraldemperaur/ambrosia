package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.CuisineCommand;
import iot.empiaurhouse.ambrosia.model.Cuisine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuisineCommandToCuisineTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    CuisineCommandToCuisine testConverter;

    @BeforeEach
    void setUp() {
        testConverter = new CuisineCommandToCuisine();
    }

    @Test
    public void testNullObject() throws Exception{
        assertNull(testConverter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception{
        assertNotNull(testConverter.convert(new CuisineCommand()));
    }


    @Test
    public void convert() throws Exception{

        CuisineCommand cuisineCommand = new CuisineCommand();
        cuisineCommand.setId(ID_VALUE);
        cuisineCommand.setCuisineDescription(DESCRIPTION);
        Cuisine cuisine = testConverter.convert(cuisineCommand);
        assertEquals(ID_VALUE, cuisine.getId());
        assertEquals(DESCRIPTION, cuisine.getCuisineDescription());


    }
}