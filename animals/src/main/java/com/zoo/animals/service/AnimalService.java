package com.zoo.animals.service;

import com.zoo.animals.model.Animal;
import com.zoo.animals.view.CountZoosWithAnimal;

import java.util.ArrayList;

public interface AnimalService {
    ArrayList<Animal> findAll();

    Animal findAnimalByName(String name);

    ArrayList<CountZoosWithAnimal> getCountOfZoosWithAnimal();
}
