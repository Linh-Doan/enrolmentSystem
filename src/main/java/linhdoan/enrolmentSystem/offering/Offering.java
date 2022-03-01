package linhdoan.enrolmentSystem.offering;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import linhdoan.enrolmentSystem.assessment.Assessment;
import linhdoan.enrolmentSystem.student.Student;
import linhdoan.enrolmentSystem.unit.Unit;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(name = "Offering")
@Table(
        name = "offering",
        uniqueConstraints = {@UniqueConstraint(name = "unique_offering", columnNames = {"unit_code", "offering_year", "offering_semester"})}
)
public class Offering {
    @Id
    @SequenceGenerator(
            name = "offering_sequence",
            sequenceName = "offering_sequence",
            allocationSize =  1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "offering_sequence"
    )
    @Column(
            name = "offering_id",
            updatable = false
    )
    private Integer offeringId;

    @ManyToOne
    @JoinColumn(
            name = "unit_code",
            nullable = false
    )
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"offerings"})
    private Unit unit;

    @Column(
            name = "offering_year",
            nullable = false
    )
    private Integer offeringYear;

    @Column(
            name = "offering_semester",
            nullable = false
    )
    private Character offeringSemester;

    @ManyToMany(mappedBy = "enrolledOfferings", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"enrolledOfferings"})
    private List<Student> currentStudents;

    @OneToMany(mappedBy = "offering", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"offering"})
    private List<Assessment> assessments;

    public Offering() {
    }

    public Offering(Unit unit, Integer offeringYear, Character offeringSemester) {
        this.unit = unit;
        this.offeringYear = offeringYear;
        this.offeringSemester = offeringSemester;
    }

    public Integer getOfferingId() {
        return offeringId;
    }

    public void setOfferingId(Integer offeringId) {
        this.offeringId = offeringId;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Integer getOfferingYear() {
        return offeringYear;
    }

    public void setOfferingYear(Integer offeringYear) {
        this.offeringYear = offeringYear;
    }

    public Character getOfferingSemester() {
        return offeringSemester;
    }

    public void setOfferingSemester(Character offeringSemester) {
        this.offeringSemester = offeringSemester;
    }

    private List<Student> getCurrentStudentsInternal() {
        if (currentStudents == null) {
            currentStudents = new ArrayList<>();
        }
        return currentStudents;
    }

    public List<Student> getCurrentStudents() {
        List<Student> sortedStudents = new ArrayList<>(getCurrentStudentsInternal());
        PropertyComparator.sort(sortedStudents, new MutableSortDefinition("studentId", true, true));
        return Collections.unmodifiableList(sortedStudents);
    }

    protected boolean isNew() {
        return getOfferingId() == null;
    }

    public void addCurrentStudents(Student student) {
        getCurrentStudentsInternal().add(student);
    }

    private List<Assessment> getAssessmentsInternal() {
        if (assessments == null) {
            assessments = new ArrayList<>();
        }
        return assessments;
    }

    public List<Assessment> getAssessments() {
        List<Assessment> sortedAssessments = new ArrayList<>(getAssessmentsInternal());
        PropertyComparator.sort(sortedAssessments, new MutableSortDefinition("assessmentDate", true, true));
        return Collections.unmodifiableList(sortedAssessments);
    }

    public void addAssessment(Assessment assessment) {
        getAssessmentsInternal().add(assessment);
        assessment.setOffering(this);
    }

    public void removeStudentFromCurrentStudents(Integer studentId) {
        for (Student student : currentStudents){
            if (student.getStudentId().equals(studentId)){
                currentStudents.remove(student);
                break;
            }
        }
    }
}

