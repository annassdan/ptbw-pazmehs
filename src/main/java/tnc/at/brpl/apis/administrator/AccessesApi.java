package tnc.at.brpl.apis.administrator;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tnc.at.brpl.models.administrator.Accesses;
import tnc.at.brpl.services.administrator.AccessesService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.ApiModel;

@RestController
@RequestMapping(value = Brpl.API +
        Brpl.PACKAGE_ADMINISTRATOR +
        Brpl.URL_DISPATCHER + Brpl.CONTENT.ACCESSES + Brpl.URL_DISPATCHER,
        produces = { Brpl.MODE.JSON })
public class AccessesApi extends ApiModel<Accesses, AccessesService> {
}
