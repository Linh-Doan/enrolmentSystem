//package linhdoan.enrolmentSystem.mapper;
//
//
//import linhdoan.enrolmentSystem.offering.Offering;
//import linhdoan.enrolmentSystem.offering.OfferingCreationDTO;
//import linhdoan.enrolmentSystem.offering.OfferingId;
//import linhdoan.enrolmentSystem.unit.Unit;
//import linhdoan.enrolmentSystem.unit.UnitRepository;
//import org.mapstruct.Mapper;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Optional;
//
//@Mapper(componentModel = "spring")
//public abstract class OfferingMapper {
//    @Autowired
//    protected UnitRepository unitRepository;
//
//    public Offering toOffering(OfferingCreationDTO dto) {
//        OfferingId id = new OfferingId(dto.getUnitCode(), dto.getOfferingYear(), dto.getOfferingSemester());
//        Offering newOffer = new Offering(id);
//        Optional<Unit> enteredUnit = unitRepository.findById(dto.getUnitCode());
//        if (enteredUnit.isPresent()) {
//            newOffer.setUnit(enteredUnit.get());
//            return newOffer;
//        } else {
//            throw new IllegalStateException("Unit " + dto.getUnitCode() + " does not exist");
//        }
//    }
//}