//package linhdoan.enrolmentSystem.offering;
//import linhdoan.enrolmentSystem.unit.Unit;
//import linhdoan.enrolmentSystem.unit.UnitRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//@DataJpaTest
//@RunWith(SpringRunner.class)
//public class OfferingRepositoryTest {
//    @Autowired
//    OfferingRepository repository;
//    @Autowired
//    UnitRepository unitRepository;
//    @Autowired
//    TestEntityManager testEntityManager;
//
//    @Test
//    public void repoCorrectlySavesGivenOffering() {
//        Unit fit2101 = new Unit("FIT2101", "Project management");
//        Unit fit2107 = new Unit("FIT2107", "Quality assurance");
//        unitRepository.saveAll(List.of(fit2101, fit2107));
//        OfferingId id = new OfferingId("FIT2107", LocalDate.of(2021, 1, 1), '1');
//        Offering offer = new Offering(id);
//        offer.setUnit(unitRepository.getById("FIT2107"));
//        repository.save(offer);
//        Offering result = testEntityManager.find(Offering.class, id);
//        assertEquals(result.getOfferingId().getUnitCode(), offer.getOfferingId().getUnitCode());
//        assertEquals(result.getOfferingId().getOfferingSemester(), offer.getOfferingId().getOfferingSemester());
//    }
//}
