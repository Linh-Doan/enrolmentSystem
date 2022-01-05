package linhdoan.enrolmentSystem.offering;
import linhdoan.enrolmentSystem.unit.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Offering> getOfferings(@PathVariable("unitCode") String unitCode) {
        return offeringService.getOfferings(unitCode);
    }

    @PostMapping(path = "{unitCode}/new")
    public Integer addOffering(@RequestBody Offering offering, @PathVariable("unitCode") String unitCode) {
        return offeringService.addOffering(offering, unitCode);
    }

    @GetMapping(path = "id/{offeringId}")
    public Offering getOfferingById(@PathVariable("offeringId") Integer offeringId) {
        return offeringService.getOfferingById(offeringId);
    }
}
