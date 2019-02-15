package tnc.at.brpl.apis.integration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tnc.at.brpl.models.integration.Sizing;
import tnc.at.brpl.services.integration.SizingService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.ApiModel;

@RestController
@RequestMapping(value = "/api/integrasi/sizing/", produces = Brpl.MODE.JSON)
public class SizingApi extends ApiModel<Sizing, SizingService> {
}
