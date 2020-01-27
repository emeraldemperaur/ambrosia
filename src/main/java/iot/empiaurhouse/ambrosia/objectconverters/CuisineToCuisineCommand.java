package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.CuisineCommand;
import iot.empiaurhouse.ambrosia.model.Cuisine;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CuisineToCuisineCommand implements Converter<Cuisine, CuisineCommand> {

    @Synchronized
    @Nullable
    @Override
    public CuisineCommand convert(Cuisine cuisine) {
        if(cuisine == null){
            return null;
        }

        final CuisineCommand cuisineCommand = new CuisineCommand();
        cuisineCommand.setId(cuisine.getId());
        cuisineCommand.setCuisineDescription(cuisine.getCuisineDescription());

        return cuisineCommand;
    }
}
