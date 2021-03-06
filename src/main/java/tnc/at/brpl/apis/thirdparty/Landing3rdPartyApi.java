package tnc.at.brpl.apis.thirdparty;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tnc.at.brpl.exceptions.ResourceInternalServerErrorException;
import tnc.at.brpl.models.main.dto.BiologyOnReproduction3rdPartyDTO;
import tnc.at.brpl.models.main.dto.BiologyOnSize3rdPartyDTO;
import tnc.at.brpl.models.main.dto.Landing3rdPartyDTO;
import tnc.at.brpl.models.main.dto.Operational3rdPartyDTO;
import tnc.at.brpl.services.thirdparty.Landing3rdPartyService;
import tnc.at.brpl.utils.Brpl;

@RestController
@Transactional
@RequestMapping(value = "/sampling/api/v1", produces = {Brpl.MODE.JSON})
@SuppressWarnings("unused")
public class Landing3rdPartyApi {

    @Autowired
    Landing3rdPartyService landing3rdPartyService;


    /**
     * Malekukan save data partner brpl ke dalam sistem ebrpl
     * @return
     */
    @PostMapping("/transfer")
    public ResponseEntity<?> save3rdPartyData(@RequestBody Landing3rdPartyDTO landing3rdPartyDTO) {
        return ResponseEntity.ok(landing3rdPartyService.save(landing3rdPartyDTO));
    }

    @PostMapping("/transfer/update")
    public ResponseEntity<?> update3rdPartyData(@RequestBody Landing3rdPartyDTO landing3rdPartyDTO) {
        return ResponseEntity.ok(landing3rdPartyService.update(landing3rdPartyDTO));
    }

    @PostMapping("/transfer/non-trip/biologiukuran")
    public ResponseEntity<?> save3rdPartyDataNonTripBiologiUkuran(@RequestBody BiologyOnSize3rdPartyDTO biologyOnSize3rdPartyDTO) {
        return ResponseEntity.ok(landing3rdPartyService.saveNonTripBiologiUkuran(biologyOnSize3rdPartyDTO));
    }

    @PostMapping("/transfer/non-trip/biologireproduksi")
    public ResponseEntity<?> save3rdPartyDataNonTripBiologiReproduksi(@RequestBody BiologyOnReproduction3rdPartyDTO biologyOnReproduction3rdPartyDTO) {
        return ResponseEntity.ok(landing3rdPartyService.saveNonTripBiologiReproduksi(biologyOnReproduction3rdPartyDTO));
    }

    @PostMapping("/transfer/non-trip/operasional")
    public ResponseEntity<?> save3rdPartyDataNonTripOperasional(@RequestBody Operational3rdPartyDTO operational3rdPartyDTO) {
        return ResponseEntity.ok(landing3rdPartyService.saveNonTripOperasional(operational3rdPartyDTO));
    }



    @GetMapping(params = {"page", "size"})
    public ResponseEntity<?> getAll3rdPartyData(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(landing3rdPartyService.getAll3rdPartyData(page, size));
    }


    @GetMapping("/getby/{id}")
    public ResponseEntity<?> getById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok(landing3rdPartyService.getById3rdPartyData(id));
    }


    @GetMapping("/get/operasional/by/{id}")
    public ResponseEntity<?> getOperasionalById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok(landing3rdPartyService.getOperasionalById3rdPartyData(id));
    }


    @GetMapping("/get/biologiukuran/by/{id}")
    public ResponseEntity<?> getUkuranById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok(landing3rdPartyService.getUkuranById3rdPartyData(id));
    }


    @GetMapping("/get/biologireproduksi/by/{id}")
    public ResponseEntity<?> getReproduksiById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok(landing3rdPartyService.getReproduksiById3rdPartyData(id));
    }


    @DeleteMapping("/deleteby/{id}")
    public ResponseEntity<?> deleteById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok(landing3rdPartyService.deleteById3rdPartyData(id));
    }


    @DeleteMapping("/delete/operasional/by/{id}")
    public ResponseEntity<?> deleteOperasionalById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok(landing3rdPartyService.deleteOperasionalById3rdPartyData(id));
    }


    @DeleteMapping("/delete/biologiukuran/by/{id}")
    public ResponseEntity<?> deleteUkuranById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok(landing3rdPartyService.deleteUkuranById3rdPartyData(id));
    }


    @DeleteMapping("/delete/biologireproduksi/by/{id}")
    public ResponseEntity<?> deleteReproduksiById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok(landing3rdPartyService.deleteReproduksiById3rdPartyData(id));
    }


}
