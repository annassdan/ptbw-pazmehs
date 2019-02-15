package tnc.at.brpl.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tnc.at.brpl.models.master.Enumerator;
import tnc.at.brpl.utils.Brpl;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@RestController
@RequestMapping(value = "/testlink")
@SuppressWarnings("unused")
public class TestLink {

    @GetMapping
    public ResponseEntity testLink(){
        return new ResponseEntity(Enumerator.builder().emailEnumerator("").build(), null, HttpStatus.OK);
    }

}
