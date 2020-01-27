package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.SynopsisCommand;
import iot.empiaurhouse.ambrosia.model.Synopsis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SynopsisToSynopsisCommandTest {

    public static final Long ID_VALUE = new Long(1L);
    public static final String RECIPE_NOTES = "Notes";
    SynopsisToSynopsisCommand testConverter;



    @BeforeEach
    void setUp() throws Exception {
        testConverter = new SynopsisToSynopsisCommand();
    }

    @Test
    void convert() throws Exception {
        Synopsis synopsis = new Synopsis();
        synopsis.setId(ID_VALUE);
        synopsis.setRecipeSynopsis(RECIPE_NOTES);
        SynopsisCommand synopsisCommand = testConverter.convert(synopsis);
        assertEquals(ID_VALUE, synopsisCommand.getId());
        assertEquals(RECIPE_NOTES, synopsisCommand.getRecipeSynopsis());

    }

    @Test
    public void testNull() throws Exception {
        assertNull(testConverter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(testConverter.convert(new Synopsis()));
    }



}