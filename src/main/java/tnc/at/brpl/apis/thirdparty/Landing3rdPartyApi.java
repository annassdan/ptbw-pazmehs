package tnc.at.brpl.apis.thirdparty;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tnc.at.brpl.exceptions.ResourceInternalServerErrorException;
import tnc.at.brpl.models.main.dto.BiologyOnReproduction3rdPartyDTO;
import tnc.at.brpl.models.main.dto.BiologyOnSize3rdPartyDTO;
import tnc.at.brpl.models.main.dto.Landing3rdPartyDTO;
import tnc.at.brpl.models.main.dto.Operational3rdPartyDTO;
import tnc.at.brpl.services.thirdparty.Delete3rdPartyResponse;
import tnc.at.brpl.services.thirdparty.Landing3rdPartyService;
import tnc.at.brpl.utils.Brpl;

@RestController
@RequestMapping(value = "/x/sampling/api/v1", produces = {Brpl.MODE.JSON})
@SuppressWarnings("unused")
public class Landing3rdPartyApi {

    @Autowired
    Landing3rdPartyService landing3rdPartyService;

//
//    @GetMapping("/get/operasional/by/{id}")
//    public ResponseEntity<?> getOperasionalById3rdPartyData(@PathVariable("id") String id) {
//        return ResponseEntity.ok(landing3rdPartyService.getOperasionalById3rdPartyData(id));
//    }
//
//
//    @GetMapping("/get/biologiukuran/by/{id}")
//    public ResponseEntity<?> getUkuranById3rdPartyData(@PathVariable("id") String id) {
//        return ResponseEntity.ok(landing3rdPartyService.getUkuranById3rdPartyData(id));
//    }
//
//
//    @GetMapping("/get/biologireproduksi/by/{id}")
//    public ResponseEntity<?> getReproduksiById3rdPartyData(@PathVariable("id") String id) {
//        return ResponseEntity.ok(landing3rdPartyService.getReproduksiById3rdPartyData(id));
//    }
//
//
//
//
//
//    @DeleteMapping("/delete/operasional/by/{id}")
//    public ResponseEntity<?> deleteOperasionalById3rdPartyData(@PathVariable("id") String id) {
//        return ResponseEntity.ok(landing3rdPartyService.deleteOperasionalById3rdPartyData(id));
//    }
//
//
//    @DeleteMapping("/delete/biologiukuran/by/{id}")
//    public ResponseEntity<?> deleteUkuranById3rdPartyData(@PathVariable("id") String id) {
//        return ResponseEntity.ok(landing3rdPartyService.deleteUkuranById3rdPartyData(id));
//    }
//
//
//    @DeleteMapping("/delete/biologireproduksi/by/{id}")
//    public ResponseEntity<?> deleteReproduksiById3rdPartyData(@PathVariable("id") String id) {
//        return ResponseEntity.ok(landing3rdPartyService.deleteReproduksiById3rdPartyData(id));
//    }


}
