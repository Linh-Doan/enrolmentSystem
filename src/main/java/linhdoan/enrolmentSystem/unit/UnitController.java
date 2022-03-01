package linhdoan.enrolmentSystem.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public UnitController() {
    }

    @GetMapping
    public List<Unit> getAllUnits(){
        return unitService.getAllUnits();
    }

    @GetMapping(path = "{unitCode}")
    public ResponseEntity getUnitByUnitCode(@PathVariable("unitCode") String unitCode){
        Unit unit = null;
        try {
            unit = unitService.getUnitByUnitCode(unitCode);
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Unit>(unit, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addNewUnit(@RequestBody Unit newUnit) {
        try {
            this.unitService.addNewUnit(newUnit);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newUnit, HttpStatus.CREATED);
    }
}
