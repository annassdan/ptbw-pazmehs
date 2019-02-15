
package tnc.at.brpl.apis.master;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tnc.at.brpl.models.master.Researcher;
import tnc.at.brpl.services.master.ResearcherService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.ApiModel;

/**
 * Copyright (c) 2017
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */

@RestController
@Transactional
@RequestMapping(value = Brpl.API +
        Brpl.PACKAGE_MASTER +
        Brpl.URL_DISPATCHER + Brpl.CONTENT.RESEARCHER  + Brpl.URL_DISPATCHER,
                produces = { Brpl.MODE.JSON })
@SuppressWarnings("unused")
public class ResearcherApi extends ApiModel<Researcher, ResearcherService> {
}
