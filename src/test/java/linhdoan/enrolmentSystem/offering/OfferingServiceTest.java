package linhdoan.enrolmentSystem.offering;

import linhdoan.enrolmentSystem.unit.Unit;
import linhdoan.enrolmentSystem.unit.UnitRepository;
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
public class OfferingServiceTest {
    @InjectMocks
    private OfferingService offeringService;

    @Mock
    private OfferingRepository offeringRepository;
    @Mock
    private UnitRepository unitRepository;

    private Unit unit;
    private Offering offering;


    @Before
    public void setUp() {
        unit = new Unit("FIT1001", "Introduction");
        offering = new Offering(unit, 2022, '1');
    }

    @Test
    public void givenValidUnitCode_whenGetOfferingsByUnitCode_returnJsonArray() throws Exception {
        given(unitRepository.findById(unit.getUnitCode())).willReturn(Optional.of(unit));
        given(offeringRepository.findByUnitCode(unit.getUnitCode())).willReturn(List.of(offering));
        List<Offering> offeringsFound = offeringService.getOfferings(unit.getUnitCode());
        assertEquals(offeringsFound.size(), 1);
        assertEquals(offeringsFound.get(0), offering);
    }

    @Test
    public void givenInvalidUnitCode_whenGetOfferingsByUnitCode_throwException() throws Exception {
        given(unitRepository.findById(unit.getUnitCode())).willReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            offeringService.getOfferings(unit.getUnitCode());
        });
    }

    @Test
    public void givenValidOfferingAndUnitCode_whenAddNewOffering_thenSaved() throws Exception {
        given(unitRepository.findById(unit.getUnitCode())).willReturn(Optional.of(unit));
        Offering added = offeringService.addOffering(offering, unit.getUnitCode());
        assertEquals(offering, added);
        verify(offeringRepository, times(1)).save(any(Offering.class));
    }

    @Test
    public void givenInvalidUnitCode_whenAddNewOffering_thenThrowException() throws Exception {
        given(unitRepository.findById(unit.getUnitCode())).willReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            offeringService.addOffering(offering, unit.getUnitCode());
        });
    }

    @Test
    public void givenExistingOffering_whenAddOffering_thenThrowException() throws Exception {
        unit.addOffering(offering);
        given(unitRepository.findById(unit.getUnitCode())).willReturn(Optional.of(unit));
        assertThrows(IllegalStateException.class, () -> {
            offeringService.addOffering(offering, unit.getUnitCode());
        });
    }

    @Test
    public void givenInvalidOffering_whenAddNewOffering_thenThrowException() throws Exception {
        offering.setOfferingId(1);
        assertThrows(IllegalStateException.class, () -> {
            offeringService.addOffering(offering, unit.getUnitCode());
        });
    }

    @Test
    public void whenGetAllOfferings_thenReturnJsonArray() throws Exception{
        given(offeringRepository.findAll()).willReturn(List.of(offering));
        List<Offering> offerings = offeringService.getAllOfferings();
        assertEquals(offerings.size(), 1);
        assertEquals(offerings.get(0), offering);
    }


    @Test
    public void givenValidOfferingId_whenGetOfferingByOfferingId_returnOffering() throws Exception {
        offering.setOfferingId(1);
        given(offeringRepository.findById(offering.getOfferingId())).willReturn(Optional.of(offering));
        Offering offeringFound = offeringService.getOfferingById(offering.getOfferingId());
        assertEquals(offeringFound, offering);
    }

    @Test
    public void givenInvalidUnitCode_whenGetUnitByUnitCode_throwException() throws Exception {
        offering.setOfferingId(1);
        given(offeringRepository.findById(offering.getOfferingId())).willReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            offeringService.getOfferingById(offering.getOfferingId());
        });
    }


}
