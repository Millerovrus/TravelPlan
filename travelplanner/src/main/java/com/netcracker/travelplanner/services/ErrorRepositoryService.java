package com.netcracker.travelplanner.services;

import com.netcracker.travelplanner.model.entities.IntegrationError;
import com.netcracker.travelplanner.repository.IntegrationErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErrorRepositoryService {

    @Autowired
    private IntegrationErrorRepository repository;


    //в описание пишем понятное описание ошибки, можно много символов, в модуллТайтл название класса, в котором ошибка выскочила
    public void saveError(IntegrationError error) {
        repository.save(error);
    }

    public void saveErrors(Iterable<IntegrationError> errors){
        repository.save(errors);
    }
}
