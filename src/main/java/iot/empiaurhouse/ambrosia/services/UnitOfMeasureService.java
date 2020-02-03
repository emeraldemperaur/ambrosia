package iot.empiaurhouse.ambrosia.services;

import iot.empiaurhouse.ambrosia.commandobjects.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUnitsOfMeasure();

}
