package linhdoan.enrolmentSystem.offering;

import linhdoan.enrolmentSystem.unit.Unit;
import linhdoan.enrolmentSystem.unit.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OfferingService {
    private OfferingRepository offeringRepository;
    private UnitRepository unitRepository;

    @Autowired
    public OfferingService(OfferingRepository offeringRepository, UnitRepository unitRepository) {
        this.offeringRepository = offeringRepository;
        this.unitRepository = unitRepository;
    }

    public List<Offering> getOfferings(String unitCode) {
        if (!unitRepository.findById(unitCode).isPresent()) {
            throw new IllegalStateException("Unit " + unitCode + " does not exist");
        }
        return offeringRepository.findByUnitCode(unitCode);
    }

    public Offering addOffering(Offering newOffering, String unitCode) {
        if (newOffering.isNew()) {
            if (!unitRepository.findById(unitCode).isPresent()) {
                throw new IllegalStateException("Unit " + unitCode + " does not exist");
            }
            Unit unit = unitRepository.findById(unitCode).get();
            if (unit.getOffering(newOffering.getOfferingYear(), newOffering.getOfferingSemester()) != null) {
                throw new IllegalStateException("Unit " + unitCode + " year " + newOffering.getOfferingYear() + " semester " + newOffering.getOfferingSemester() + " already exists");
            }
            unit.addOffering(newOffering);
            offeringRepository.save(newOffering);
        }
        return newOffering;
    }

    public List<Offering> getAllOfferings() {
        return offeringRepository.findAll();
    }

    public Offering getOfferingById(Integer offeringId) {
        Offering offering = offeringRepository.findById(offeringId).orElseThrow(() -> new IllegalStateException("Invalid offering id " + offeringId));
        return offering;
    }
}
