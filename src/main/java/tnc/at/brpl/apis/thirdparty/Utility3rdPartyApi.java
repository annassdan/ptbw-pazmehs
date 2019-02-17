package tnc.at.brpl.apis.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tnc.at.brpl.services.thirdparty.Utility3rdPartyService;
import tnc.at.brpl.utils.Brpl;

@RestController
@RequestMapping(value = "/api/client/util",
        produces = {Brpl.MODE.JSON})
@SuppressWarnings("unused")
public class Utility3rdPartyApi {

    @Autowired
    Utility3rdPartyService utility3rdPartyService;

    @GetMapping("/tkg")
    public ResponseEntity<?> tkg() {
        return ResponseEntity.ok(utility3rdPartyService.tkg());
    }

}
