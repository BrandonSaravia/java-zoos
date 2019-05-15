package com.zoo.animals.repo;

import com.zoo.animals.model.Animal;
import com.zoo.animals.view.CountZoosWithAnimal;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    Animal findByAnimaltype(String name);

    @Modifying
    @Query(value = "SELECT s.animalid, animaltype, count(zooid) as zoocount FROM zooanimals s INNER JOIN animal c on s.animalid = c.animalid GROUP BY s.animalid, animaltype", nativeQuery = true)
    ArrayList<CountZoosWithAnimal> getCountOfZoosWithAnimal();
}
