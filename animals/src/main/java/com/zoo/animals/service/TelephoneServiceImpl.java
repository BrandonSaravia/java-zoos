package com.zoo.animals.service;

import com.zoo.animals.repo.TelephoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "telephoneService")
public class TelephoneServiceImpl implements TelephoneService{

    @Autowired
    private TelephoneRepository telephonerepos;


}
