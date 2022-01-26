package linhdoan.enrolmentSystem.assessment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import linhdoan.enrolmentSystem.offering.Offering;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "Assessment")
@Table(
        name = "assessment",
        uniqueConstraints = {@UniqueConstraint(name = "unique_assessment", columnNames = {"offering_id", "assessment_date"})}
)
public class Assessment {
    @Id
    @SequenceGenerator(
            name = "assessment_assessment_id_sequence",
            sequenceName = "assessment_assessment_id_sequence",
            allocationSize =  1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "assessment_assessment_id_sequence"
    )
    @Column(
            name = "assessment_id",
            updatable = false
    )
    private Integer assessmentId;

    @ManyToOne
    @JoinColumn(
            name = "offering_id",
            nullable = false
    )
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"assessments", "currentStudents", "offeringId", "offeringYear", "offeringSemester"})
    private Offering offering;

    @Column(
            name = "assessment_date",
            nullable = false
    )
    private LocalDate assessmentDate;

    @Column(
            name = "assessment_weight",
            nullable = false
    )
    private Float assessmentWeight;

    public Assessment() {
    }

    public Assessment(Offering offering, LocalDate assessmentDate) {
        this.offering = offering;
        this.assessmentDate = assessmentDate;
    }

    public Integer getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Integer assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Offering getOffering() {
        return offering;
    }

    public void setOffering(Offering offering) {
        this.offering = offering;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public Float getAssessmentWeight() {
        return assessmentWeight;
    }

    public void setAssessmentWeight(Float assessmentWeight) {
        this.assessmentWeight = assessmentWeight;
    }
}
