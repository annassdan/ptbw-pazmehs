package tnc.at.brpl.apis.thirdparty.sampling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tnc.at.brpl.models.main.dto.Landing3rdPartyDTO;
import tnc.at.brpl.services.thirdparty.Delete3rdPartyResponse;
import tnc.at.brpl.services.thirdparty.sampling.TripService;
import tnc.at.brpl.utils.Brpl;
import tnc.at.brpl.utils.data.DataOrder;

@RestController
@Transactional
@RequestMapping(value = "/sampling/api/v1/trip", produces = {Brpl.MODE.JSON})
public class TripApi {

    @Autowired
    TripService tripService;


    @PostMapping()
    public ResponseEntity<Landing3rdPartyDTO> saveTrip(@RequestBody Landing3rdPartyDTO landing3rdPartyDTO) {
        return ResponseEntity.ok(tripService.save(landing3rdPartyDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<Landing3rdPartyDTO> updateTrip(@RequestBody Landing3rdPartyDTO landing3rdPartyDTO) {
        return ResponseEntity.ok(tripService.update(landing3rdPartyDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Delete3rdPartyResponse> deleteTrip(@PathVariable("id") String id) {
        return ResponseEntity.ok(tripService.delete(id));
    }

    @GetMapping("/get-one/{id}")
    public ResponseEntity<Landing3rdPartyDTO> getOne(@PathVariable("id") String id) {
        return ResponseEntity.ok(tripService.getOne(id));
    }

    @GetMapping(value = "/get-all", params = {"page", "size", "order"})
    public ResponseEntity<Page<Landing3rdPartyDTO>> getAll(@RequestParam("page") int page,
                                                           @RequestParam("size") int size,
                                                           @RequestParam("order") DataOrder order) {
        return ResponseEntity.ok(tripService.getAll(page, size, order));
    }


}
