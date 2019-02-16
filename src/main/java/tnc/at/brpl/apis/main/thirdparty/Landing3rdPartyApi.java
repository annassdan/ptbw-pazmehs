package tnc.at.brpl.apis.main.thirdparty;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tnc.at.brpl.models.main.dto.Landing3rdPartyDTO;
import tnc.at.brpl.services.thirdparty.Landing3rdPartyService;
import tnc.at.brpl.utils.Brpl;

@RestController
@Transactional
@RequestMapping(value = "/ebridge/landing",
        produces = {Brpl.MODE.JSON})
public class Landing3rdPartyApi {

    @Autowired
    Landing3rdPartyService landing3rdPartyService;


    /**
     * Malekukan save data partner brpl ke dalam sistem ebrpl
     * @param landing3rdPartyDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<?> save3rdPartyData(@RequestBody Landing3rdPartyDTO landing3rdPartyDTO) {
        return ResponseEntity.ok(landing3rdPartyService.save(landing3rdPartyDTO));
    }




}
