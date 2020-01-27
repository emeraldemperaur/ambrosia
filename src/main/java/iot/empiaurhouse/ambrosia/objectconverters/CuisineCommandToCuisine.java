package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.CuisineCommand;
import iot.empiaurhouse.ambrosia.model.Cuisine;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CuisineCommandToCuisine implements Converter<CuisineCommand, Cuisine> {

    @Synchronized
    @Nullable
    @Override
    public Cuisine convert(CuisineCommand cuisineCommand) {
        if(cuisineCommand == null){
            return null;
        }
        final Cuisine cuisine = new Cuisine();
        cuisine.setId(cuisineCommand.getId());
        cuisine.setCuisineDescription(cuisineCommand.getCuisineDescription());
        return cuisine;
    }
}
