package linhdoan.enrolmentSystem.student;

import linhdoan.enrolmentSystem.assessment.Assessment;
import linhdoan.enrolmentSystem.offering.Offering;
import linhdoan.enrolmentSystem.offering.OfferingRepository;
import linhdoan.enrolmentSystem.unit.Unit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class StudentServiceTest {
    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private OfferingRepository offeringRepository;

    private Student student;

    @Before
    public void setUp() {
        student = new Student("Ben", "Smith", "ben.smith@gmail.com", LocalDate.of(1998, Month.JANUARY, 3));
        student.setStudentId(1);
    }

    @Test
    public void whenGetAllStudents_thenReturnJsonArray() throws Exception{
        given(studentRepository.findAll()).willReturn(List.of(student));
        List<Student> students = studentService.getStudents();
        assertEquals(students.size(), 1);
        assertEquals(students.get(0), student);
    }

    @Test
    public void givenValidNewStudent_whenAddNewStudent_thenNoExceptionThrown() throws Exception {
        given(studentRepository.findStudentByEmail(student.getEmail())).willReturn(Optional.empty());
        studentService.addNewStudent(student);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void givenStudentWithExistingEmail_whenAddUnit_thenExceptionThrown() throws Exception {
        given(studentRepository.findStudentByEmail(student.getEmail())).willReturn(Optional.of(student));
        assertThrows(IllegalStateException.class, () -> {
            studentService.addNewStudent(student);
        });
    }

    @Test
    public void givenValidId_whenGetStudent_returnStudent() throws Exception {
        given(studentRepository.findById(student.getStudentId())).willReturn(Optional.of(student));
        Student studentFound = studentService.getStudentById(student.getStudentId());
        assertEquals(studentFound, student);
    }

    @Test
    public void givenInvalidId_whenGetStudent_throwException() throws Exception {
        given(studentRepository.findById(student.getStudentId())).willReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            studentService.getStudentById(student.getStudentId());
        });
    }

    @Test
    public void givenInvalidStudentId_whenDeleteEnrolment_throwException() throws Exception {
        Integer offeringIdToBeDeleted = 1;
        given(studentRepository.findById(student.getStudentId())).willReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            studentService.deleteEnrolment(student.getStudentId(), offeringIdToBeDeleted);
        });
    }

    @Test
    public void givenValidStudentIdAndInvalidOfferingId_whenDeleteEnrolment_throwException() throws Exception {
        Integer offeringIdToBeDeleted = 1;
        given(studentRepository.findById(student.getStudentId())).willReturn(Optional.of(student));
        assertThrows(IllegalStateException.class, () -> {
            studentService.deleteEnrolment(student.getStudentId(), offeringIdToBeDeleted);
        });
    }

    @Test
    public void givenValidStudentIdAndOfferingId_whenDeleteEnrolment_throwException() throws Exception {
        Offering offering = new Offering();
        offering.setOfferingId(1);
        student.addEnrolledOffering(offering);
        assertTrue(student.getEnrolledOfferings().contains(offering));
        given(studentRepository.findById(student.getStudentId())).willReturn(Optional.of(student));
        studentService.deleteEnrolment(student.getStudentId(), offering.getOfferingId());
        assertFalse(student.getEnrolledOfferings().contains(offering));
    }

    @Test
    public void givenInvalidStudentId_whenAddEnrolment_throwException() throws Exception {
        Integer offeringIdToBeAdded = 1;
        given(studentRepository.findById(student.getStudentId())).willReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            studentService.addEnrolment(student.getStudentId(), offeringIdToBeAdded);
        });
    }

    @Test
    public void givenInvalidOfferingId_whenAddEnrolment_throwException() throws Exception {
        Integer offeringIdToBeAdded = 1;
        given(studentRepository.findById(student.getStudentId())).willReturn(Optional.of(student));
        given(offeringRepository.findById(offeringIdToBeAdded)).willReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            studentService.addEnrolment(student.getStudentId(), offeringIdToBeAdded);
        });
    }

    @Test
    public void givenEnrolledOfferingId_whenAddEnrolment_throwException() throws Exception {
        Offering offering = new Offering();
        offering.setOfferingId(1);
        student.addEnrolledOffering(offering);
        given(studentRepository.findById(student.getStudentId())).willReturn(Optional.of(student));
        given(offeringRepository.findById(offering.getOfferingId())).willReturn(Optional.of(offering));
        assertThrows(IllegalStateException.class, () -> {
            studentService.addEnrolment(student.getStudentId(), offering.getOfferingId());
        });
    }

    @Test
    public void givenValidOfferingId_whenAddEnrolment_throwException() throws Exception {
        Offering offering = new Offering();
        offering.setOfferingId(1);
        given(studentRepository.findById(student.getStudentId())).willReturn(Optional.of(student));
        given(offeringRepository.findById(offering.getOfferingId())).willReturn(Optional.of(offering));
        studentService.addEnrolment(student.getStudentId(), offering.getOfferingId());
        assertTrue(student.getEnrolledOfferings().contains(offering));
    }

    @Test
    public void givenInvalidStudentId_whenGetSchedule_throwException() throws Exception {
        given(studentRepository.findById(student.getStudentId())).willReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            studentService.getSchedule(student.getStudentId());
        });
    }

    @Test
    public void givenValidStudentId_whenGetSchedule_returnSchedule() throws Exception {
        Assessment assessment = new Assessment();
        Offering offering = new Offering();
        offering.addAssessment(assessment);
        student.addEnrolledOffering(offering);
        given(studentRepository.findById(student.getStudentId())).willReturn(Optional.of(student));
        List<Assessment> assessments = studentService.getSchedule(student.getStudentId());
        assertEquals(assessments.size(), 1);
        assertEquals(assessments.get(0), assessment);

    }
}
