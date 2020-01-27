package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.UnitOfMeasureCommand;
import iot.empiaurhouse.ambrosia.model.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure != null){
            final UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
            unitOfMeasureCommand.setId(unitOfMeasure.getId());
            unitOfMeasureCommand.setUom(unitOfMeasure.getUom());
            return unitOfMeasureCommand;
        }
        return null;
    }
}
