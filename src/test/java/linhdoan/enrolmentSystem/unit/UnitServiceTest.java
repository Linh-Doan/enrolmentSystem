package linhdoan.enrolmentSystem.unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class UnitServiceTest {

    @InjectMocks
    private UnitService unitService;

    @Mock
    private UnitRepository unitRepository;

    private Unit unit;

    @Before
    public void setUp() {
        unit = new Unit("FIT1001", "Introduction");
    }

    @Test
    public void whenGetAllUnits_thenReturnJsonArray() throws Exception{
        given(unitRepository.findAll()).willReturn(List.of(unit));
        List<Unit> units = unitService.getAllUnits();
        assertEquals(units.size(), 1);
        assertEquals(units.get(0), unit);
    }

    @Test
    public void givenValidNewUnit_whenAddNewUnit_thenNoExceptionThrown() throws Exception {
        given(unitRepository.findById(unit.getUnitCode())).willReturn(Optional.empty());
        unitService.addNewUnit(unit);
        verify(unitRepository, times(1)).save(any(Unit.class));
    }

    @Test
    public void givenExistingUnit_whenAddUnit_thenExceptionThrown() throws Exception {
        given(unitRepository.findById(unit.getUnitCode())).willReturn(Optional.of(unit));
        assertThrows(IllegalStateException.class, () -> {
            unitService.addNewUnit(unit);
        });
    }

    @Test
    public void givenValidUnitCode_whenGetUnitByUnitCode_returnUnit() throws Exception {
        given(unitRepository.findById(unit.getUnitCode())).willReturn(Optional.of(unit));
        Unit unitFound = unitService.getUnitByUnitCode(unit.getUnitCode());
        assertEquals(unitFound, unit);
    }

    @Test
    public void givenInvalidUnitCode_whenGetUnitByUnitCode_throwException() throws Exception {
        given(unitRepository.findById(unit.getUnitCode())).willReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            unitService.getUnitByUnitCode(unit.getUnitCode());
        });
    }

}
