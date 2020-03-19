package tnc.at.brpl.apis.thirdparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tnc.at.brpl.services.thirdparty.Utility3rdPartyService;
import tnc.at.brpl.utils.Brpl;

@RestController
@RequestMapping(value = "/sampling/api/util",
        produces = {Brpl.MODE.JSON})
@SuppressWarnings("unused")
public class Utility3rdPartyApi {

    @Autowired
    Utility3rdPartyService utility3rdPartyService;


    @GetMapping("/tkg")
    public ResponseEntity<?> tkg() {
        return ResponseEntity.ok(utility3rdPartyService.tkg());
    }


    @GetMapping("/resources")
    public ResponseEntity<?> sumberdaya() {
        return ResponseEntity.ok(utility3rdPartyService.sumberDaya());
    }


    @GetMapping(value = "/fishinggears", params = {"sumberdaya"})
    public ResponseEntity<?> alatTangkap(@RequestParam("sumberdaya") String sumberdaya) {
        return ResponseEntity.ok(utility3rdPartyService.alatTangkap(sumberdaya));
    }



    @GetMapping(value = "/fishinggear/spec", params = {"sumberdaya", "alattangkap"})
    public ResponseEntity<?> spec(@RequestParam("sumberdaya") String sumberdaya, @RequestParam("alattangkap") String alattangkap) {
        return ResponseEntity.ok(utility3rdPartyService.listSpesifikasi(sumberdaya, alattangkap));
    }



}
