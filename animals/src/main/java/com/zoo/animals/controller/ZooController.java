package com.zoo.animals.controller;

import com.zoo.animals.model.Animal;
import com.zoo.animals.model.Zoo;
import com.zoo.animals.service.AnimalService;
import com.zoo.animals.service.ZooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/zoos")
public class ZooController {

    @Autowired
    private ZooService zooService;
    @Autowired
    private AnimalService animalService;



    @GetMapping(value = "/zoos", produces = {"application/json"})
    public ResponseEntity<?> listAllZoos() {

        ArrayList<Zoo> allZoos = zooService.findAll();

        return new ResponseEntity<>(allZoos, HttpStatus.OK);
    }

    @GetMapping(value = "/{name}", produces = {"application/json"})
    public ResponseEntity<?> getZooName(@PathVariable String name) {

        Zoo specificZoo = zooService.findZooByName(name);

        return new ResponseEntity<>(specificZoo , HttpStatus.OK);
    }

    @GetMapping(value = "/animals/animals", produces = {"application/json"})
    public ResponseEntity<?> getAllAnimals() {
        ArrayList<Animal> allAnimals = animalService.findAll();
        return new ResponseEntity<>(allAnimals, HttpStatus.OK);
    }

    @GetMapping(value = "/animals/{name}", produces = {"application/json"})
    public ResponseEntity<?> getAnimamalName(@PathVariable String name) {

        Animal specificAnimal = animalService.findAnimalByName(name);

        return new ResponseEntity<>(specificAnimal , HttpStatus.OK);
    }


    @GetMapping(value = "/animals/count", produces = {"application/json"})
    public ResponseEntity<?> getCountOfZoosWithAnimal() {

    return new ResponseEntity<>(animalService.getCountOfZoosWithAnimal(), HttpStatus.OK);

    }

    @GetMapping(value = "/admin/zoos/{id}")
    public ResponseEntity<?> deleteZoo(@PathVariable long id) {
        zooService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/admin/zoos", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewZoo(@Valid @RequestBody Zoo newZoo) throws URISyntaxException {
        newZoo = zooService.save(newZoo);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newZooURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/zooid").buildAndExpand(newZoo.getZooid()).toUri();
        responseHeaders.setLocation(newZooURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/admin/zoos/{id}")
    public ResponseEntity<?> updateZoo(@RequestBody Zoo updateZoo, @PathVariable long id) {
        zooService.update(updateZoo, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    @GetMapping(value = "/admin/zoos/{zooid}/animals/{animalid}")
//    public ResponseEntity<?> deleteSpecifiedAnimal(@PathVariable("zooid", long zooid), @PathVariable("animalid") long animalid) {
//        zooService.delete()
//    }

}

//    Stretch Goals
//    Expose the end point DELETE /admin/zoos/{zooid}/animals/{animalid} - delete the zoo animal combination based off of ids.
//        Hint: @PathVariable("zooid", long zooid), @PathVariable("animalid") long animalid
//
//    Expose the end point POST /admin/zoos/{zooid}/animals/{animalid} - adds the zoo animal combination based off of ids.
//        Hint: @PathVariable("zooid", long zooid), @PathVariable("animalid") long animalid
//        Log to the console each time a record in the database is changed.
