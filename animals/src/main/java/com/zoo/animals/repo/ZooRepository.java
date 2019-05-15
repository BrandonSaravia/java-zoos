package com.zoo.animals.repo;

import com.zoo.animals.model.Zoo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ZooRepository extends CrudRepository<Zoo, Long> {

    Zoo findByZooname(String name);

    @Modifying
    @Query(value = "DELETE FROM zooanimals WHERE zooid = :id", nativeQuery = true)
    void deleteZooFromZooAnimals(long id);
}
