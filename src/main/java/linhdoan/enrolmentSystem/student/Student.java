package linhdoan.enrolmentSystem.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import linhdoan.enrolmentSystem.offering.Offering;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(name = "Student")
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(name = "student_address_unique", columnNames = "email")
        }
)
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_student_id_sequence",
            sequenceName = "student_student_id_sequence",
            allocationSize =  1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_student_id_sequence"
    )
    @Column(
            name = "student_id",
            updatable = false
    )
    private Integer studentId;

    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false
    )
    private String email;
    @Column(
            name = "dob",
            nullable = false
    )
    private LocalDate dob;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_offering",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "offering_id")
    )
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"currentStudents"})
    private List<Offering> enrolledOfferings;


    public Student() {
    }

    public Student(String firstName, String lastName, String email, LocalDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer id) {
        this.studentId = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    private List<Offering> getEnrolledOfferingsInternal() {
        if (enrolledOfferings == null) {
            enrolledOfferings = new ArrayList<>();
        }
        return enrolledOfferings;
    }

    public List<Offering> getEnrolledOfferings() {
        List<Offering> sortedOfferings = new ArrayList<>(getEnrolledOfferingsInternal());
        PropertyComparator.sort(sortedOfferings, new MutableSortDefinition("offeringYear", true, true));
        return Collections.unmodifiableList(sortedOfferings);
    }

    public void addEnrolledOffering(Offering offering) {
        getEnrolledOfferingsInternal().add(offering);
        offering.addCurrentStudents(this);
    }

    protected void removeOfferingFromEnrolledOfferings(Integer offeringId) {
        for (Offering offering : enrolledOfferings) {
            if (offeringId.equals(offering.getOfferingId())) {
                offering.removeStudentFromCurrentStudents(studentId);
                enrolledOfferings.remove(offering);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                '}';
    }
}
