package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.UnitOfMeasureCommand;
import iot.empiaurhouse.ambrosia.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final String DESCRIPTION = "test_description";
    public static final Long LONG_VALUE = 1L;
    UnitOfMeasureToUnitOfMeasureCommand testConverter;


    @BeforeEach
    void setUp() throws Exception {
        testConverter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObjectConvert() throws Exception {
        assertNull(testConverter.convert(null));
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertNotNull(testConverter.convert(new UnitOfMeasure()));
    }

    @Test
    void convert() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(LONG_VALUE);
        unitOfMeasure.setUom(DESCRIPTION);
        UnitOfMeasureCommand unitOfMeasureCommand = testConverter.convert(unitOfMeasure);
        assertEquals(LONG_VALUE, unitOfMeasureCommand.getId());
        assertEquals(DESCRIPTION, unitOfMeasureCommand.getUom());
    }
}