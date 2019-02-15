package tnc.at.brpl.apis.main;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tnc.at.brpl.models.main.OperationalOnFishingToolSpecification;
import tnc.at.brpl.services.main.OperationalOnFishingToolSpecificationService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.ApiModel;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */

@RestController
@Transactional
@RequestMapping(value = Brpl.API +
        Brpl.PACKAGE_CORE +
        Brpl.URL_DISPATCHER + Brpl.CONTENT.OPERATIONAL_ON_FISHING_TOOL_SPEC + Brpl.URL_DISPATCHER,
        produces = { Brpl.MODE.JSON })
@SuppressWarnings("unused")
public class OperationalOnFishingToolSpecificationApi extends ApiModel<OperationalOnFishingToolSpecification, OperationalOnFishingToolSpecificationService> {



}
