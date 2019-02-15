package tnc.at.brpl.apis.integration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tnc.at.brpl.models.integration.Deepslope;
import tnc.at.brpl.services.integration.DeepslopeService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.api.ApiModel;

@RestController
@RequestMapping(value = "/api/integrasi/deepslope/", produces = Brpl.MODE.JSON)
public class DeepslopeApi extends ApiModel<Deepslope, DeepslopeService> {
}
