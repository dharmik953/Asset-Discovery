package com.serviceops.assetdiscovery.controller;

import com.serviceops.assetdiscovery.rest.CredentialsRest;
import com.serviceops.assetdiscovery.service.interfaces.CredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CredentialsController {
    private final CredentialsService credentialsService;
    private static final Logger logger = LoggerFactory.getLogger(CredentialsController.class);

    public CredentialsController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @GetMapping(value = "/credentials/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CredentialsRest getCredential(@PathVariable Long id) {
        logger.debug("Finding credentials with id ->{}", id);
        return credentialsService.findById(id);
    }

    @GetMapping(value = "/credentials", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CredentialsRest> getAllCredentials() {
        logger.debug("Finding all credentials");
        return credentialsService.findAll();
    }

    @PutMapping(value = "/credentials/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateCredential(@PathVariable("id") Long id, @RequestBody CredentialsRest credentialsRest) {
        logger.debug("Updating Credentials with id -> {}", id);
        credentialsService.update(id, credentialsRest);
    }

    @PostMapping(value = "/credentials", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CredentialsRest> addCredentials(@RequestBody CredentialsRest credentialsRest) {
        logger.debug("Creating Credentials with username -> {}", credentialsRest.getUsername());
        return new ResponseEntity<>(credentialsService.save(credentialsRest), HttpStatus.CREATED);
    }

    @DeleteMapping("/credentials/{id}")
    public void deleteCredentialsById(@PathVariable Long id) {
        logger.debug("Deleting Credentials with id -> {}", id);
        credentialsService.deleteById(id);
    }

}
