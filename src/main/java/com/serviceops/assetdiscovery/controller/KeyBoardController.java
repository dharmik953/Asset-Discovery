package com.serviceops.assetdiscovery.controller;


import com.serviceops.assetdiscovery.rest.KeyboardRest;
import com.serviceops.assetdiscovery.service.interfaces.KeyboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KeyBoardController {
    private final KeyboardService keyboardService;
    private final Logger logger = LoggerFactory.getLogger(KeyBoardController.class);

    public KeyBoardController(KeyboardService keyboardService) {
        this.keyboardService = keyboardService;
    }

    @GetMapping(value = "/{refId}/keyboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<KeyboardRest> getKeyboard(@PathVariable("refId") Long refId) {
        logger.debug("Fetching KeyBoard with Asset id -> {}", refId);
        return keyboardService.findAllByRefId(refId);
    }

    @DeleteMapping(value = "/{refId}/keyboard/{id}")
    public void deleteKeyBoard(@PathVariable("refId") Long refId, @PathVariable("id") long id) {
        logger.debug("Deleting Keyboard with  id -> {}", id);
        keyboardService.deleteByRefId(refId, id);
    }

    @PutMapping(value = "/{refId}/keyboard/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateKeyboard(@PathVariable("refId") Long refId, @PathVariable("id") Long id,
            @RequestBody KeyboardRest keyboardRest) {
        logger.debug("Updating Keyboard with Asset id -> {}", refId);
        keyboardService.update(refId, id, keyboardRest);
    }
}
