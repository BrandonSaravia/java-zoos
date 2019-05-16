package com.zoo.animals.service;

import com.zoo.animals.model.Zoo;

import java.util.ArrayList;
import java.util.List;

public interface ZooService {
    ArrayList<Zoo> findAll();

    Zoo findZooByName(String name);

    void delete(long id);

    Zoo save(Zoo zoo);

    Zoo update(Zoo zoo, long id);

}
