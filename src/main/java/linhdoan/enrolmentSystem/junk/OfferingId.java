//package linhdoan.enrolmentSystem.offering;
//
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import java.io.Serializable;
//import java.time.LocalDate;
//import java.util.Objects;
//
//@Embeddable
//public class OfferingId implements Serializable {
//    @Column(name = "unit_code")
//    private String unitCode;
//
//    @Column(name = "offering_year")
//    private LocalDate offeringYear;
//
//    @Column(name = "offering_semester")
//    private Character offeringSemester;
//
//    public OfferingId() {
//    }
//
//    public OfferingId(String unitCode, LocalDate offeringYear, Character offeringSemester) {
//        this.unitCode = unitCode;
//        this.offeringYear = offeringYear;
//        this.offeringSemester = offeringSemester;
//    }
//
//    public String getUnitCode() {
//        return unitCode;
//    }
//
//    public void setUnitCode(String unitCode) {
//        this.unitCode = unitCode;
//    }
//
//    public LocalDate getOfferingYear() {
//        return offeringYear;
//    }
//
//    public void setOfferingYear(LocalDate offeringYear) {
//        this.offeringYear = offeringYear;
//    }
//
//    public Character getOfferingSemester() {
//        return offeringSemester;
//    }
//
//    public void setOfferingSemester(Character offeringSemester) {
//        this.offeringSemester = offeringSemester;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//        OfferingId otherOffering = (OfferingId) obj;
//        return offeringYear.getYear() == otherOffering.getOfferingYear().getYear() &&
//                offeringSemester.equals(otherOffering.getOfferingSemester());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(offeringYear, offeringSemester);
//    }
//}
