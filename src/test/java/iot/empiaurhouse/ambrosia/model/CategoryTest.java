package iot.empiaurhouse.ambrosia.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CategoryTest {

    Category category;

    @Before
    public void setUp(){
        category = new Category();
    }

    @org.junit.Test
    public void getId() throws Exception {

        Long id = 4L;
        category.setId(id);
        assertEquals(id, category.getId());

    }

    @org.junit.Test
    public void getCategoryDescription() throws Exception {

        String desc = "This is a test description";
        category.setCategoryDescription(desc);
        assertEquals(desc, category.getCategoryDescription());

    }

    @Test
    public void getRecipes() throws Exception{


    }
}