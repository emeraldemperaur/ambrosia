package iot.empiaurhouse.ambrosia.repositories;

import iot.empiaurhouse.ambrosia.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByCategoryDescription(String categoryDescription);

}
