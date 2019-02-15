package tnc.at.brpl.apis.integration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tnc.at.brpl.models.integration.Boat;
import tnc.at.brpl.services.integration.BoatService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.ApiModel;

@RestController
@RequestMapping(value = "/api/integrasi/boat/", produces = Brpl.MODE.JSON)
public class BoatApi extends ApiModel<Boat, BoatService> {
}
