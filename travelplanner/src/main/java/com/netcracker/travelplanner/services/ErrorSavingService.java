package com.netcracker.travelplanner.services;

import com.netcracker.travelplanner.models.entities.IntegrationError;
import com.netcracker.travelplanner.models.entities.User;
import com.netcracker.travelplanner.repository.IntegrationErrorRepository;
import com.netcracker.travelplanner.security.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ErrorSavingService {
    private final IntegrationErrorRepository repository;

    private final SecurityService securityService;

    private final UserRepositoryService userService;

    @Autowired
    public ErrorSavingService(IntegrationErrorRepository repository, SecurityService securityService, UserRepositoryService userService) {
        this.repository = repository;
        this.securityService = securityService;
        this.userService = userService;
    }

    public void saveError(String description, String moduleTitle){
        String email = securityService.findLoggedInUsername();

        if (email != null) {
            User user = userService.findByEmail(email);
            repository.save(new IntegrationError(moduleTitle, new Date(), description));
        }
    }
}
