package iot.empiaurhouse.ambrosia.repositories;

import iot.empiaurhouse.ambrosia.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UnitOfMeasureRepositoryTest {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() throws Exception {

    }

    @Test
    void findByUom() throws Exception {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByUom("Pint");
        assertEquals("Pint",unitOfMeasureOptional.get().getUom());
    }
}