package com.netcracker.travelplanner.services;

import com.netcracker.travelplanner.model.entities.IntegrationError;
import com.netcracker.travelplanner.repository.IntegrationErrorRepository;
import com.netcracker.travelplanner.security.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ErrorSavingService {
    private final IntegrationErrorRepository repository;

    @Autowired
    public ErrorSavingService(IntegrationErrorRepository repository, SecurityService securityService, UserRepositoryService userService) {
        this.repository = repository;
    }

    //в описание пишем понятное описание ошибки, можно много символов, в модуллТайтл название класса, в котором ошибка выскочила
    public void saveError(String description, String moduleTitle) {
        repository.save(new IntegrationError(moduleTitle, new Date(), description));
    }
}
