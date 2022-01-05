package linhdoan.enrolmentSystem.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {
    private UnitRepository unitRepository;

    @Autowired
    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public void addNewUnit(Unit newUnit) {
        Optional<Unit> existingUnit = unitRepository.findById(newUnit.getUnitCode());
        if (existingUnit.isPresent()) {
            throw new IllegalStateException("Unit code " + newUnit.getUnitCode() + " already exists");
        }
        unitRepository.save(newUnit);
    }

    public Unit getUnitByUnitCode(String unitCode) {
        Unit unit = unitRepository.findById(unitCode).orElseThrow(() -> new IllegalStateException("Invalid unit code " + unitCode));
        return unit;
    }
}
