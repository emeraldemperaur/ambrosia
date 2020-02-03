package iot.empiaurhouse.ambrosia.services;

import iot.empiaurhouse.ambrosia.commandobjects.UnitOfMeasureCommand;
import iot.empiaurhouse.ambrosia.model.UnitOfMeasure;
import iot.empiaurhouse.ambrosia.objectconverters.UnitOfMeasureToUnitOfMeasureCommand;
import iot.empiaurhouse.ambrosia.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void listAllUnitsOfMeasure() {
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(1L);
        unitOfMeasures.add(unitOfMeasure);

        UnitOfMeasure unitOfMeasureMock = new UnitOfMeasure();
        unitOfMeasureMock.setId(2L);
        unitOfMeasures.add(unitOfMeasureMock);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUnitsOfMeasure();
        assertEquals(2, unitOfMeasureCommands.size());
        verify(unitOfMeasureRepository, times(1)).findAll();

    }
}