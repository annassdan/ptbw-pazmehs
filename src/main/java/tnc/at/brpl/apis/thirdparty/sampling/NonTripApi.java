package tnc.at.brpl.apis.thirdparty.sampling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tnc.at.brpl.models.main.dto.BiologyOnReproduction3rdPartyDTO;
import tnc.at.brpl.models.main.dto.BiologyOnSize3rdPartyDTO;
import tnc.at.brpl.models.main.dto.Operational3rdPartyDTO;
import tnc.at.brpl.services.thirdparty.Delete3rdPartyResponse;
import tnc.at.brpl.services.thirdparty.sampling.NonTripService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.data.DataOrder;

@RestController
@RequestMapping(value = "/sampling/api/v1/non-trip", produces = {Brpl.MODE.JSON})
public class NonTripApi implements Brpl {

    @Autowired
    NonTripService nonTripService;

    /* non trip operational */
    @PostMapping("/operasional")
    public ResponseEntity<Operational3rdPartyDTO> saveDataNonTripOperasional(@RequestBody Operational3rdPartyDTO operational3rdPartyDTO) {
        return ResponseEntity.ok(nonTripService.saveNonTripOperasional(operational3rdPartyDTO));
    }

    @PostMapping("/operasional/update")
    public ResponseEntity<Operational3rdPartyDTO> updateNonTripOperasional(@RequestBody Operational3rdPartyDTO operational3rdPartyDTO) {
        return ResponseEntity.ok(nonTripService.updateNonTripOperasional(operational3rdPartyDTO));
    }

    @DeleteMapping("/operasional/delete/{id}")
    public ResponseEntity<Delete3rdPartyResponse> deleteNonTripOperasional(@PathVariable("id") String id) {
        return ResponseEntity.ok(nonTripService.deleteNonTripOperasional(id));
    }

    @GetMapping("/operasional/get-one/{id}")
    public ResponseEntity<Operational3rdPartyDTO> getOneNonTripOperasional(@PathVariable("id") String id) {
        return ResponseEntity.ok(nonTripService.getOneNonTripOperasional(id));
    }

    @GetMapping(value = "/operasional/get-all", params = {"page", "size", "order"})
    public ResponseEntity<Page<Operational3rdPartyDTO>> getAllNonTripOperasional(@RequestParam("page") int page,
                                                                                 @RequestParam("size") int size,
                                                                                 @RequestParam("order") DataOrder order) {
        return ResponseEntity.ok(nonTripService.getAllNonTripOperasional(page, size, order));
    }
    /* end of non-trip operational */


    /* non trip biology on size */
    @PostMapping("/biologiukuran")
    public ResponseEntity<BiologyOnSize3rdPartyDTO> saveNonTripBiologyOnSize(@RequestBody BiologyOnSize3rdPartyDTO biologyOnSize3rdPartyDTO) {
        return ResponseEntity.ok(nonTripService.saveNonTripBiologyOnSize(biologyOnSize3rdPartyDTO));
    }

    @PostMapping("/biologiukuran/update")
    public ResponseEntity<BiologyOnSize3rdPartyDTO> updateNonTripBiologyOnSize(@RequestBody BiologyOnSize3rdPartyDTO biologyOnSize3rdPartyDTO) {
        return ResponseEntity.ok(nonTripService.updateNonTripBiologyOnSize(biologyOnSize3rdPartyDTO));
    }

    @DeleteMapping("/biologiukuran/delete/{id}")
    public ResponseEntity<Delete3rdPartyResponse> deleteNonTripBiologyOnSize(@PathVariable("id") String id) {
        return ResponseEntity.ok(nonTripService.deleteNonTripBiologyOnSize(id));
    }

    @GetMapping("/biologiukuran/get-one/{id}")
    public ResponseEntity<BiologyOnSize3rdPartyDTO> getOneNonTripBiologyOnSize(@PathVariable("id") String id) {
        return ResponseEntity.ok(nonTripService.getOneNonTripBiologyOnSize(id));
    }

    @GetMapping(value = "/biologiukuran/get-all", params = {"page", "size", "order"})
    public ResponseEntity<Page<BiologyOnSize3rdPartyDTO>> getAllNonTripBiologyOnSize(@RequestParam("page") int page,
                                                                                     @RequestParam("size") int size,
                                                                                     @RequestParam("order") DataOrder order) {
        return ResponseEntity.ok(nonTripService.getAllNonTripBiologyOnSize(page, size, order));
    }
    /* end of non-trip biology on size */


    /* non trip biology on reproduction  */
    @PostMapping("/biologireproduksi")
    public ResponseEntity<BiologyOnReproduction3rdPartyDTO> saveNonTripBiologyOnReproduction(@RequestBody BiologyOnReproduction3rdPartyDTO biologyOnReproduction3rdPartyDTO) {
        return ResponseEntity.ok(nonTripService.saveNonTripBiologyOnReproduction(biologyOnReproduction3rdPartyDTO));
    }

    @PostMapping("/biologireproduksi/update")
    public ResponseEntity<BiologyOnReproduction3rdPartyDTO> updateNonTripBiologyOnReproduction(@RequestBody BiologyOnReproduction3rdPartyDTO biologyOnReproduction3rdPartyDTO) {
        return ResponseEntity.ok(nonTripService.updateNonTripBiologyOnReproduction(biologyOnReproduction3rdPartyDTO));
    }

    @DeleteMapping("/biologireproduksi/delete/{id}")
    public ResponseEntity<Delete3rdPartyResponse> deleteNonTripBiologyOnReproduction(@PathVariable("id") String id) {
        return ResponseEntity.ok(nonTripService.deleteNonTripBiologyOnReproduction(id));
    }

    @GetMapping("/biologireproduksi/get-one/{id}")
    public ResponseEntity<BiologyOnReproduction3rdPartyDTO> getOneNonTripBiologyOnReproduction(@PathVariable("id") String id) {
        return ResponseEntity.ok(nonTripService.getOneNonTripBiologyOnReproduction(id));
    }

    @GetMapping(value = "/biologireproduksi/get-all", params = {"page", "size", "order"})
    public ResponseEntity<Page<BiologyOnReproduction3rdPartyDTO>> getAllNonTripBiologyOnReproduction(@RequestParam("page") int page,
                                                                                                     @RequestParam("size") int size,
                                                                                                     @RequestParam("order") DataOrder order) {
        return ResponseEntity.ok(nonTripService.getAllNonTripBiologyOnReproduction(page, size, order));
    }
    /* end of non-trip biology on size */


}
