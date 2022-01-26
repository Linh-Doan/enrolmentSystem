package linhdoan.enrolmentSystem.student;

import linhdoan.enrolmentSystem.assessment.Assessment;
import linhdoan.enrolmentSystem.offering.Offering;
import linhdoan.enrolmentSystem.offering.OfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final OfferingRepository offeringRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository, OfferingRepository offeringRepository) {
        this.studentRepository = studentRepository;
        this.offeringRepository = offeringRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Integer addNewStudent(Student student) {
        Optional<Student> studentFound = studentRepository.findStudentByEmail(student.getEmail());
        if (studentFound.isPresent()) {
            throw new IllegalStateException("Email " + student.getEmail() +" has been taken");
        }
        studentRepository.save(student);
        return student.getStudentId();
    }

    @Transactional
    public void deleteEnrolment(Integer studentId, Integer offeringId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Invalid student id " + studentId));
        List<Offering> offeringsList = student.getEnrolledOfferings();
        boolean offeringFound = false;
        for (Offering offering: offeringsList) {
            if (offering.getOfferingId().equals(offeringId)) {
                offeringFound = true;
                break;
            }
        }
        if (!offeringFound) {
            throw new IllegalStateException("Offering id " + offeringId + " not found for student id " + studentId);
        }
        student.removeOfferingFromEnrolledOfferings(offeringId);
    }

    @Transactional
    public void addEnrolment(Integer studentId, Integer offeringId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Invalid student id " + studentId));
        Offering offering = offeringRepository.findById(offeringId).orElseThrow(() -> new IllegalStateException("Invalid offering id " + offeringId));
        List<Offering> currentOfferings = student.getEnrolledOfferings();
        for (Offering o : currentOfferings) {
            if (o.getOfferingId().equals(offeringId)) {
                throw new IllegalStateException("Student " + studentId + " is already enrolled in offering " + offeringId);
            }
        }
        student.addEnrolledOffering(offering);
    }

    public Student getStudentById(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Invalid student id "+studentId));
        return student;
    }

    public List<Assessment> getSchedule(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Invalid student id " +  studentId));
        List<Assessment> schedule = new ArrayList<>();
        List<Offering> offerings = student.getEnrolledOfferings();
        for (Offering offering : offerings) {
            schedule.addAll(offering.getAssessments());
        }
        return schedule;
    }
}
