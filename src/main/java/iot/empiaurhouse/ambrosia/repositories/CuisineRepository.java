package iot.empiaurhouse.ambrosia.repositories;

import iot.empiaurhouse.ambrosia.model.Cuisine;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CuisineRepository extends CrudRepository<Cuisine,Long> {

    Optional<Cuisine> findByCuisineDescription(String cuisineDescription);


}
