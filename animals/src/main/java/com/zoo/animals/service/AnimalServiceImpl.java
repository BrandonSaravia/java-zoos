package com.zoo.animals.service;

import com.zoo.animals.model.Animal;
import com.zoo.animals.repo.AnimalRepository;
import com.zoo.animals.view.CountZoosWithAnimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

@Service(value = "animalService")
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    private AnimalRepository animalrepos;

    @Override
    public ArrayList<Animal> findAll() {
        ArrayList<Animal> list = new ArrayList<>();
        animalrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Animal findAnimalByName(String name) {

        Animal animal = animalrepos.findByAnimaltype(name);

        if(animal == null) {
            throw new EntityNotFoundException("Animal " + name + " not found");
        } else {
            return animal;
        }
    }

    @Override
    public ArrayList<CountZoosWithAnimal> getCountOfZoosWithAnimal() {
        return animalrepos.getCountOfZoosWithAnimal();
    }
}
