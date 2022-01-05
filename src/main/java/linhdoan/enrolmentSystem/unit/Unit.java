package linhdoan.enrolmentSystem.unit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import linhdoan.enrolmentSystem.offering.Offering;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity(name = "unit")
@Table(
        name = "unit"
)
public class Unit {
    @Id
    @Column(
            name = "unit_code",
            nullable = false
    )
    private String unitCode;

    @Column(
            name = "unit_name",
            nullable = false
    )
    private String unitName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unit", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"unit", "currentStudents", "assessments"})
    private List<Offering> offerings;

    public Unit() {
    }

    public Unit(String unitCode, String unitName) {
        this.unitCode = unitCode;
        this.unitName = unitName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    private List<Offering> getOfferingsInternal() {
        if (offerings == null) {
            offerings = new ArrayList<>();
        }
        return offerings;
    }

    public List<Offering> getOfferings() {
        List<Offering> sortedOfferings = new ArrayList<>(getOfferingsInternal());
        PropertyComparator.sort(sortedOfferings, new MutableSortDefinition("offeringYear", true, true));
        return Collections.unmodifiableList(sortedOfferings);
    }

    public Offering getOffering(Integer year, Character sem) {
        for (Offering offering: getOfferingsInternal()) {
            if (Objects.equals(offering.getOfferingYear(), year) && sem.equals(offering.getOfferingSemester())) {
                return offering;
            }
        }
        return null;
    }

    public void addOffering(Offering offering) {
        getOfferingsInternal().add(offering);
        offering.setUnit(this);
    }

    @Override
    public String toString() {
        return "Unit{" +
                "unitCode='" + unitCode + '\'' +
                ", unitName='" + unitName + '\'' +
                '}';
    }
}
