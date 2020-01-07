package iot.empiaurhouse.ambrosia.repositories;

import iot.empiaurhouse.ambrosia.model.Cuisine;
import iot.empiaurhouse.ambrosia.model.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByUom(String uom);


}
