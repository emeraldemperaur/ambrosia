package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.CuisineCommand;
import iot.empiaurhouse.ambrosia.model.Cuisine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuisineToCuisineCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    CuisineToCuisineCommand testConverter;

    @BeforeEach
    public void setUp() throws Exception {
        testConverter = new CuisineToCuisineCommand();
    }

    @Test
    public void testNullObject() throws Exception{
        assertNull(testConverter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception{
        assertNotNull(testConverter.convert(new Cuisine()));
    }

    @Test
    public void convert() throws Exception {
        Cuisine cuisine = new Cuisine();
        cuisine.setId(ID_VALUE);
        cuisine.setCuisineDescription(DESCRIPTION);

        CuisineCommand cuisineCommand = testConverter.convert(cuisine);

        assert cuisineCommand != null;
        assertEquals(ID_VALUE, cuisineCommand.getId());
        assertEquals(DESCRIPTION, cuisineCommand.getCuisineDescription());

    }
}