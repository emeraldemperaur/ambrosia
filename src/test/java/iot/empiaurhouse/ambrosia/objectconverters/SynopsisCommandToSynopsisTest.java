package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.SynopsisCommand;
import iot.empiaurhouse.ambrosia.model.Synopsis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SynopsisCommandToSynopsisTest {
    public static final Long ID_VALUE = new Long(1L);
    public static final String TEST_SYNOPSIS = "Test Synopsis";
    SynopsisCommandToSynopsis testConverter;

    @BeforeEach
    void setUp() throws Exception {
        testConverter = new SynopsisCommandToSynopsis();
    }

    @Test
    public void testNullParameter() throws Exception {
        assertNull(testConverter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(testConverter.convert(new SynopsisCommand()));
    }


    @Test
    void convert() throws Exception {

        SynopsisCommand synopsisCommand = new SynopsisCommand();
        synopsisCommand.setId(ID_VALUE);
        synopsisCommand.setRecipeSynopsis(TEST_SYNOPSIS);
        Synopsis synopsis = testConverter.convert(synopsisCommand);
        assertNotNull(synopsis);
        assertEquals(ID_VALUE, synopsis.getId());
        assertEquals(TEST_SYNOPSIS, synopsis.getRecipeSynopsis());

    }
}