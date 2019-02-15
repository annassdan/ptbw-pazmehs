package tnc.at.brpl.apis.master;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tnc.at.brpl.models.master.Enumerator;
import tnc.at.brpl.services.master.EnumeratorService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.ApiModel;
import tnc.at.brpl.utils.api.response.ResponseResolver;
import tnc.at.brpl.utils.api.response.ResponseType;


/**
 * Copyright (c) 2017
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */

@RestController
@Transactional
@RequestMapping(value = Brpl.API +
                Brpl.PACKAGE_MASTER +
                Brpl.URL_DISPATCHER + Brpl.CONTENT.ENUMERATOR + Brpl.URL_DISPATCHER,
                produces = { Brpl.MODE.JSON })
@SuppressWarnings("unused")
public class EnumeratorApi extends ApiModel<Enumerator, EnumeratorService> {


    @GetMapping(value = "/join/", params = {PAGING.PAGE, PAGING.SIZE} )
    public ResponseEntity<ResponseResolver<?>> findAllWithJoin(@RequestParam(PAGING.PAGE) int page,
                                                            @RequestParam(PAGING.SIZE) int size) {

        return  ResponseResolver.builder()
                .type(ResponseType.READ)
                .httpStatus(HttpStatus.OK)
                .body(modelingResult(service.findAllWithJoin(page, size)))
                .build().map();
    }

}
