package linhdoan.enrolmentSystem.offering;
import linhdoan.enrolmentSystem.unit.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("offering")
public class OfferingController {

    private OfferingService offeringService;

    @Autowired
    public OfferingController(OfferingService offeringService) {
        this.offeringService = offeringService;
    }

    @GetMapping
    public List<Offering> getAllOfferings() {
        return offeringService.getAllOfferings();
    }

    @GetMapping(path = "{unitCode}")
    public Object getOfferings(@PathVariable("unitCode") String unitCode) {
        List<Offering> offerings = null;
        try {
            offerings = offeringService.getOfferings(unitCode);
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return offerings;
    }

    @PostMapping(path = "{unitCode}")
    public ResponseEntity addOffering(@RequestBody Offering offering, @PathVariable("unitCode") String unitCode) {
        try {
            offeringService.addOffering(offering, unitCode);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(offering, HttpStatus.CREATED);
    }

    @GetMapping(path = "id/{offeringId}")
    public ResponseEntity getOfferingById(@PathVariable("offeringId") Integer offeringId) {
        Offering offering = null;
        try {
            offering = offeringService.getOfferingById(offeringId);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Offering>(offering, HttpStatus.OK);
    }
}
