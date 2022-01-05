package linhdoan.enrolmentSystem.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "unit")
public class UnitController {
    private UnitService unitService;

    @Autowired
    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    public List<Unit> getAllUnits(){
        return unitService.getAllUnits();
    }

    @GetMapping(path = "{unitCode}")
    public Unit getUnitByUnitCode(@PathVariable("unitCode") String unitCode){
        return unitService.getUnitByUnitCode(unitCode);
    }

    @PostMapping(path = "new")
    public void addNewUnit(@RequestBody Unit newUnit) {
        this.unitService.addNewUnit(newUnit);
    }
}
