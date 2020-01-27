package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.CategoryCommand;
import iot.empiaurhouse.ambrosia.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";
    CategoryToCategoryCommand testConverter;

    @BeforeEach
    public void setUp() throws Exception {
        testConverter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullObject() throws Exception{
        assertNull(testConverter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception{
        assertNotNull(testConverter.convert(new Category()));
    }

    @Test
    public void convert() throws Exception {
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setCategoryDescription(DESCRIPTION);

        CategoryCommand categoryCommand = testConverter.convert(category);

        assert categoryCommand != null;
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getCategoryDescription());



    }
}