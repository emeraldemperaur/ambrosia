package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.UnitOfMeasureCommand;
import iot.empiaurhouse.ambrosia.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = new Long(1L);
    UnitOfMeasureCommandToUnitOfMeasure testConverter;

    @BeforeEach
    public void setUp() throws Exception {
        testConverter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullParameter() throws Exception{
        assertNull(testConverter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception{
        assertNotNull(testConverter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() throws Exception {
        UnitOfMeasureCommand testCommand = new UnitOfMeasureCommand();
        testCommand.setId(LONG_VALUE);
        testCommand.setUom(DESCRIPTION);

        UnitOfMeasure unitOfMeasure = testConverter.convert(testCommand);

        assertNotNull(unitOfMeasure);
        assertEquals(LONG_VALUE, unitOfMeasure.getId());
        assertEquals(DESCRIPTION, unitOfMeasure.getUom());

    }
}