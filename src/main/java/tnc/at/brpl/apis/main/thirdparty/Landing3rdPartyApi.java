package tnc.at.brpl.apis.main.thirdparty;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tnc.at.brpl.models.main.dto.Landing3rdPartyDTO;
import tnc.at.brpl.services.thirdparty.Landing3rdPartyService;
import tnc.at.brpl.utils.Brpl;

@RestController
@Transactional
@RequestMapping(value = "/api/client/data",
        produces = {Brpl.MODE.JSON})
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


    @GetMapping(params = {"page", "size"})
    public ResponseEntity<?> getAll3rdPartyData(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(landing3rdPartyService.getAll3rdPartyData(page, size));
    }


    @GetMapping("/getby/{id}")
    public ResponseEntity<?> getById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok(landing3rdPartyService.getById3rdPartyData(id));
    }


    @DeleteMapping("/get/operasional/by/{id}")
    public ResponseEntity<?> getOperasionalById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok("");
    }


    @DeleteMapping("/get/biologiukuran/by/{id}")
    public ResponseEntity<?> getUkuranById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok("");
    }


    @DeleteMapping("/get/biologireproduksi/by/{id}")
    public ResponseEntity<?> getReproduksiById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok("");
    }


    @DeleteMapping("/deleteby/{id}")
    public ResponseEntity<?> deleteById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok(landing3rdPartyService.deleteById3rdPartyData(id));
    }


    @DeleteMapping("/delete/operasional/by/{id}")
    public ResponseEntity<?> deleteOperasionalById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok("");
    }


    @DeleteMapping("/delete/biologiukuran/by/{id}")
    public ResponseEntity<?> deleteUkuranById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok("");
    }


    @DeleteMapping("/delete/biologireproduksi/by/{id}")
    public ResponseEntity<?> deleteReproduksiById3rdPartyData(@PathVariable("id") String id) {
        return ResponseEntity.ok("");
    }





}
